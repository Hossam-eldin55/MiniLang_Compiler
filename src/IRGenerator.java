import java.util.ArrayList;
import java.util.List;

/**
 * Intermediate Representation Generator.
 * Produces Three-Address Code (TAC) from the AST.
 *
 * TAC instruction format:
 *   result = arg1 op arg2   (binary)
 *   result = op arg1        (unary)
 *   result = arg1           (copy)
 *   IF cond GOTO label      (conditional jump)
 *   GOTO label              (unconditional jump)
 *   LABEL label:            (label definition)
 *   RETURN value            (return)
 */
public class IRGenerator {

    // ─── TAC Instruction ────────────────────────────────────────────────────────

    public static class Instruction {
        public final String op;
        public final String result;
        public final String arg1;
        public final String arg2;

        public Instruction(String op, String result, String arg1, String arg2) {
            this.op     = op;
            this.result = result;
            this.arg1   = arg1;
            this.arg2   = arg2;
        }

        @Override
        public String toString() {
            switch (op) {
                case "LABEL":  return arg1 + ":";
                case "GOTO":   return "    GOTO " + arg1;
                case "IF":     return "    IF " + arg1 + " GOTO " + arg2;
                case "IFNOT":  return "    IFNOT " + arg1 + " GOTO " + arg2;
                case "RETURN": return "    RETURN" + (arg1 != null ? " " + arg1 : "");
                case "COPY":   return "    " + result + " = " + arg1;
                case "NEG":    return "    " + result + " = -" + arg1;
                case "NOT":    return "    " + result + " = !" + arg1;
                default:       return "    " + result + " = " + arg1 + " " + op + " " + arg2;
            }
        }
    }

    // ─── State ──────────────────────────────────────────────────────────────────

    private final List<Instruction> instructions = new ArrayList<>();
    private int tempCount  = 0;
    private int labelCount = 0;

    // ─── Public API ─────────────────────────────────────────────────────────────

    public List<Instruction> generate(ASTNode node) {
        genNode(node);
        return instructions;
    }

    public void printIR() {
        System.out.println("[IR] Three-Address Code:");
        for (Instruction instr : instructions) {
            System.out.println(instr);
        }
    }

    public List<Instruction> getInstructions() { return instructions; }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private String newTemp()  { return "t" + (tempCount++); }
    private String newLabel() { return "L" + (labelCount++); }

    private void emit(String op, String result, String arg1, String arg2) {
        instructions.add(new Instruction(op, result, arg1, arg2));
    }

    // ─── Generation ─────────────────────────────────────────────────────────────

    /** Generate code for a node and return the name of the place holding its value (or null). */
    private String genNode(ASTNode node) {
        if (node instanceof ASTNode.Program)        return genProgram((ASTNode.Program) node);
        if (node instanceof ASTNode.Block)           return genBlock((ASTNode.Block) node);
        if (node instanceof ASTNode.IfStatement)     return genIf((ASTNode.IfStatement) node);
        if (node instanceof ASTNode.WhileStatement)  return genWhile((ASTNode.WhileStatement) node);
        if (node instanceof ASTNode.Assignment)      return genAssignment((ASTNode.Assignment) node);
        if (node instanceof ASTNode.ReturnStatement) return genReturn((ASTNode.ReturnStatement) node);
        if (node instanceof ASTNode.BinaryOp)        return genBinaryOp((ASTNode.BinaryOp) node);
        if (node instanceof ASTNode.UnaryOp)         return genUnaryOp((ASTNode.UnaryOp) node);
        if (node instanceof ASTNode.IntLiteral)      return String.valueOf(((ASTNode.IntLiteral) node).value);
        if (node instanceof ASTNode.FloatLiteral)    return String.valueOf(((ASTNode.FloatLiteral) node).value);
        if (node instanceof ASTNode.StringLiteral)   return "\"" + ((ASTNode.StringLiteral) node).value + "\"";
        if (node instanceof ASTNode.BoolLiteral)     return ((ASTNode.BoolLiteral) node).value ? "1" : "0";
        if (node instanceof ASTNode.Identifier)      return ((ASTNode.Identifier) node).name;
        throw new RuntimeException("IRGenerator: unknown node " + node.getClass().getSimpleName());
    }

    private String genProgram(ASTNode.Program node) {
        for (ASTNode stmt : node.statements) genNode(stmt);
        return null;
    }

    private String genBlock(ASTNode.Block node) {
        for (ASTNode stmt : node.statements) genNode(stmt);
        return null;
    }

    private String genAssignment(ASTNode.Assignment node) {
        String val = genNode(node.value);
        emit("COPY", node.variable, val, null);
        return node.variable;
    }

    private String genReturn(ASTNode.ReturnStatement node) {
        String val = node.value != null ? genNode(node.value) : null;
        emit("RETURN", null, val, null);
        return null;
    }

    private String genIf(ASTNode.IfStatement node) {
        String cond      = genNode(node.condition);
        String labelElse = newLabel();
        String labelEnd  = newLabel();

        emit("IFNOT", null, cond, labelElse);
        genNode(node.thenBlock);

        if (node.elseBlock != null) {
            emit("GOTO", null, labelEnd, null);
            emit("LABEL", null, labelElse, null);
            genNode(node.elseBlock);
            emit("LABEL", null, labelEnd, null);
        } else {
            emit("LABEL", null, labelElse, null);
        }
        return null;
    }

    private String genWhile(ASTNode.WhileStatement node) {
        String labelStart = newLabel();
        String labelEnd   = newLabel();

        emit("LABEL", null, labelStart, null);
        String cond = genNode(node.condition);
        emit("IFNOT", null, cond, labelEnd);
        genNode(node.body);
        emit("GOTO", null, labelStart, null);
        emit("LABEL", null, labelEnd, null);
        return null;
    }

    private String genBinaryOp(ASTNode.BinaryOp node) {
        String left  = genNode(node.left);
        String right = genNode(node.right);
        String temp  = newTemp();
        emit(node.op, temp, left, right);
        return temp;
    }

    private String genUnaryOp(ASTNode.UnaryOp node) {
        String operand = genNode(node.operand);
        String temp    = newTemp();
        if (node.op.equals("!")) {
            emit("NOT", temp, operand, null);
        } else {
            emit("NEG", temp, operand, null);
        }
        return temp;
    }
}
