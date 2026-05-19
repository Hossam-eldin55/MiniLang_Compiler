import java.util.ArrayList;
import java.util.List;

/**
 * Semantic Analyzer for MiniLang.
 * Performs:
 *   - Scope analysis (nested scopes for blocks)
 *   - Type inference and checking
 *   - Use-before-declare detection
 *   - Type mismatch reporting
 */
public class SemanticAnalyzer {

    private SymbolTable currentScope;
    private final List<String> errors = new ArrayList<>();
    private boolean hasReturn = false;

    public SemanticAnalyzer() {
        // Global scope
        currentScope = new SymbolTable(null);
    }

    // ─── Public API ──────────────────────────────────────────────────────────────

    public void analyze(ASTNode node) {
        analyzeNode(node);
    }

    public boolean hasErrors() { return !errors.isEmpty(); }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("[Semantic] No errors found.");
        } else {
            System.out.println("[Semantic] " + errors.size() + " error(s):");
            for (String e : errors) System.out.println("  ERROR: " + e);
        }
    }

    public void printSymbolTable() {
        System.out.println("[Semantic] Symbol Table (global scope):");
        currentScope.printTable();
    }

    // ─── Node dispatch ───────────────────────────────────────────────────────────

    private SymbolTable.Type analyzeNode(ASTNode node) {
        if (node instanceof ASTNode.Program)        return analyzeProgram((ASTNode.Program) node);
        if (node instanceof ASTNode.Block)           return analyzeBlock((ASTNode.Block) node);
        if (node instanceof ASTNode.IfStatement)     return analyzeIf((ASTNode.IfStatement) node);
        if (node instanceof ASTNode.WhileStatement)  return analyzeWhile((ASTNode.WhileStatement) node);
        if (node instanceof ASTNode.Assignment)      return analyzeAssignment((ASTNode.Assignment) node);
        if (node instanceof ASTNode.ReturnStatement) return analyzeReturn((ASTNode.ReturnStatement) node);
        if (node instanceof ASTNode.BinaryOp)        return analyzeBinaryOp((ASTNode.BinaryOp) node);
        if (node instanceof ASTNode.UnaryOp)         return analyzeUnaryOp((ASTNode.UnaryOp) node);
        if (node instanceof ASTNode.IntLiteral)      return SymbolTable.Type.INT;
        if (node instanceof ASTNode.FloatLiteral)    return SymbolTable.Type.FLOAT;
        if (node instanceof ASTNode.StringLiteral)   return SymbolTable.Type.STRING;
        if (node instanceof ASTNode.BoolLiteral)     return SymbolTable.Type.BOOL;
        if (node instanceof ASTNode.Identifier)      return analyzeIdentifier((ASTNode.Identifier) node);
        errors.add("Unknown AST node: " + node.getClass().getSimpleName());
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeProgram(ASTNode.Program node) {
        for (ASTNode stmt : node.statements) analyzeNode(stmt);
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeBlock(ASTNode.Block node) {
        enterScope();
        for (ASTNode stmt : node.statements) analyzeNode(stmt);
        exitScope();
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeIf(ASTNode.IfStatement node) {
        SymbolTable.Type condType = analyzeNode(node.condition);
        if (condType != SymbolTable.Type.BOOL && condType != SymbolTable.Type.INT
                && condType != SymbolTable.Type.UNKNOWN) {
            errors.add("If condition must be boolean or int, got: " + condType);
        }
        analyzeNode(node.thenBlock);
        if (node.elseBlock != null) analyzeNode(node.elseBlock);
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeWhile(ASTNode.WhileStatement node) {
        SymbolTable.Type condType = analyzeNode(node.condition);
        if (condType != SymbolTable.Type.BOOL && condType != SymbolTable.Type.INT
                && condType != SymbolTable.Type.UNKNOWN) {
            errors.add("While condition must be boolean or int, got: " + condType);
        }
        analyzeNode(node.body);
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeAssignment(ASTNode.Assignment node) {
        SymbolTable.Type valType = analyzeNode(node.value);

        SymbolTable.Symbol existing = currentScope.lookup(node.variable);
        if (existing != null && existing.type != SymbolTable.Type.UNKNOWN
                && existing.type != valType && valType != SymbolTable.Type.UNKNOWN) {
            // Allow int→float widening implicitly
            if (!(existing.type == SymbolTable.Type.FLOAT && valType == SymbolTable.Type.INT)) {
                errors.add("Type mismatch for '" + node.variable + "': expected "
                        + existing.type + ", got " + valType);
            }
        }
        currentScope.update(node.variable, valType);
        return valType;
    }

    private SymbolTable.Type analyzeReturn(ASTNode.ReturnStatement node) {
        hasReturn = true;
        if (node.value != null) return analyzeNode(node.value);
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeBinaryOp(ASTNode.BinaryOp node) {
        SymbolTable.Type left  = analyzeNode(node.left);
        SymbolTable.Type right = analyzeNode(node.right);

        switch (node.op) {
            case "+": case "-": case "*": case "/": case "%":
                if (left == SymbolTable.Type.STRING || right == SymbolTable.Type.STRING) {
                    if (node.op.equals("+")) return SymbolTable.Type.STRING; // string concat
                    errors.add("Operator '" + node.op + "' cannot be applied to STRING");
                    return SymbolTable.Type.UNKNOWN;
                }
                if (left == SymbolTable.Type.FLOAT || right == SymbolTable.Type.FLOAT)
                    return SymbolTable.Type.FLOAT;
                return SymbolTable.Type.INT;

            case "==": case "!=": case "<": case ">": case "<=": case ">=":
                if (left != right && left != SymbolTable.Type.UNKNOWN
                        && right != SymbolTable.Type.UNKNOWN) {
                    // Allow int/float comparison
                    boolean numeric = (left == SymbolTable.Type.INT || left == SymbolTable.Type.FLOAT)
                            && (right == SymbolTable.Type.INT || right == SymbolTable.Type.FLOAT);
                    if (!numeric) {
                        errors.add("Type mismatch in comparison: " + left + " " + node.op + " " + right);
                    }
                }
                return SymbolTable.Type.BOOL;

            case "&&": case "||":
                if (left != SymbolTable.Type.BOOL && left != SymbolTable.Type.UNKNOWN)
                    errors.add("Left operand of '" + node.op + "' must be bool, got " + left);
                if (right != SymbolTable.Type.BOOL && right != SymbolTable.Type.UNKNOWN)
                    errors.add("Right operand of '" + node.op + "' must be bool, got " + right);
                return SymbolTable.Type.BOOL;

            default:
                errors.add("Unknown operator: " + node.op);
                return SymbolTable.Type.UNKNOWN;
        }
    }

    private SymbolTable.Type analyzeUnaryOp(ASTNode.UnaryOp node) {
        SymbolTable.Type t = analyzeNode(node.operand);
        if (node.op.equals("!")) {
            if (t != SymbolTable.Type.BOOL && t != SymbolTable.Type.UNKNOWN)
                errors.add("Operator '!' requires bool operand, got " + t);
            return SymbolTable.Type.BOOL;
        }
        errors.add("Unknown unary operator: " + node.op);
        return SymbolTable.Type.UNKNOWN;
    }

    private SymbolTable.Type analyzeIdentifier(ASTNode.Identifier node) {
        SymbolTable.Symbol sym = currentScope.lookup(node.name);
        if (sym == null) {
            errors.add("Undeclared variable: '" + node.name + "'");
            return SymbolTable.Type.UNKNOWN;
        }
        return sym.type;
    }

    // ─── Scope helpers ───────────────────────────────────────────────────────────

    private void enterScope() {
        currentScope = new SymbolTable(currentScope);
    }

    private void exitScope() {
        currentScope = currentScope.getParent();
    }
}
