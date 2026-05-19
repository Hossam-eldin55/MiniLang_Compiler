import java.util.List;

/**
 * AST Node hierarchy for MiniLang / TinyLang compiler.
 * All node types used by ASTVisitor, SemanticAnalyzer, IRGenerator, and CodeGenerator.
 */
public abstract class ASTNode {

    // ─── Program ────────────────────────────────────────────────────────────────

    public static class Program extends ASTNode {
        public final List<ASTNode> statements;
        public Program(List<ASTNode> statements) { this.statements = statements; }
        @Override public String toString() { return "Program(" + statements + ")"; }
    }

    // ─── Statements ─────────────────────────────────────────────────────────────

    public static class IfStatement extends ASTNode {
        public final ASTNode condition;
        public final ASTNode thenBlock;
        public final ASTNode elseBlock; // may be null
        public IfStatement(ASTNode condition, ASTNode thenBlock, ASTNode elseBlock) {
            this.condition = condition;
            this.thenBlock = thenBlock;
            this.elseBlock = elseBlock;
        }
        @Override public String toString() {
            return "If(" + condition + ", " + thenBlock + (elseBlock != null ? ", " + elseBlock : "") + ")";
        }
    }

    public static class WhileStatement extends ASTNode {
        public final ASTNode condition;
        public final ASTNode body;
        public WhileStatement(ASTNode condition, ASTNode body) {
            this.condition = condition;
            this.body = body;
        }
        @Override public String toString() { return "While(" + condition + ", " + body + ")"; }
    }

    public static class Assignment extends ASTNode {
        public final String variable;
        public final ASTNode value;
        public Assignment(String variable, ASTNode value) {
            this.variable = variable;
            this.value = value;
        }
        @Override public String toString() { return "Assign(" + variable + ", " + value + ")"; }
    }

    public static class ReturnStatement extends ASTNode {
        public final ASTNode value; // may be null
        public ReturnStatement(ASTNode value) { this.value = value; }
        @Override public String toString() { return "Return(" + value + ")"; }
    }

    public static class Block extends ASTNode {
        public final List<ASTNode> statements;
        public Block(List<ASTNode> statements) { this.statements = statements; }
        @Override public String toString() { return "Block(" + statements + ")"; }
    }

    // ─── Expressions ────────────────────────────────────────────────────────────

    public static class BinaryOp extends ASTNode {
        public final String op;
        public final ASTNode left;
        public final ASTNode right;
        public BinaryOp(String op, ASTNode left, ASTNode right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }
        @Override public String toString() { return "BinOp(" + op + ", " + left + ", " + right + ")"; }
    }

    public static class UnaryOp extends ASTNode {
        public final String op;
        public final ASTNode operand;
        public UnaryOp(String op, ASTNode operand) {
            this.op = op;
            this.operand = operand;
        }
        @Override public String toString() { return "UnaryOp(" + op + ", " + operand + ")"; }
    }

    public static class IntLiteral extends ASTNode {
        public final int value;
        public IntLiteral(int value) { this.value = value; }
        @Override public String toString() { return "Int(" + value + ")"; }
    }

    public static class FloatLiteral extends ASTNode {
        public final double value;
        public FloatLiteral(double value) { this.value = value; }
        @Override public String toString() { return "Float(" + value + ")"; }
    }

    public static class StringLiteral extends ASTNode {
        public final String value;
        public StringLiteral(String value) { this.value = value; }
        @Override public String toString() { return "String(\"" + value + "\")"; }
    }

    public static class BoolLiteral extends ASTNode {
        public final boolean value;
        public BoolLiteral(boolean value) { this.value = value; }
        @Override public String toString() { return "Bool(" + value + ")"; }
    }

    public static class Identifier extends ASTNode {
        public final String name;
        public Identifier(String name) { this.name = name; }
        @Override public String toString() { return "ID(" + name + ")"; }
    }
}
