import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts ANTLR parse tree into a clean AST using ASTNode hierarchy.
 */
public class ASTVisitor extends TinyParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(TinyParser.ProgramContext ctx) {
        List<ASTNode> stmts = new ArrayList<>();
        for (TinyParser.StatementContext s : ctx.statement()) {
            stmts.add(visit(s));
        }
        return new ASTNode.Program(stmts);
    }

    @Override
    public ASTNode visitStatement(TinyParser.StatementContext ctx) {
        if (ctx.ifStatement() != null)     return visit(ctx.ifStatement());
        if (ctx.whileStatement() != null)  return visit(ctx.whileStatement());
        if (ctx.assignment() != null)      return visit(ctx.assignment());
        if (ctx.returnStatement() != null) return visit(ctx.returnStatement());
        if (ctx.block() != null)           return visit(ctx.block());
        throw new RuntimeException("Unknown statement: " + ctx.getText());
    }

    @Override
    public ASTNode visitIfStatement(TinyParser.IfStatementContext ctx) {
        ASTNode condition  = visit(ctx.expression());
        ASTNode thenBlock  = visit(ctx.block(0));
        ASTNode elseBlock  = ctx.block().size() > 1 ? visit(ctx.block(1)) : null;
        return new ASTNode.IfStatement(condition, thenBlock, elseBlock);
    }

    @Override
    public ASTNode visitWhileStatement(TinyParser.WhileStatementContext ctx) {
        ASTNode condition = visit(ctx.expression());
        ASTNode body      = visit(ctx.block());
        return new ASTNode.WhileStatement(condition, body);
    }

    @Override
    public ASTNode visitAssignment(TinyParser.AssignmentContext ctx) {
        String var  = ctx.ID().getText();
        ASTNode val = visit(ctx.expression());
        return new ASTNode.Assignment(var, val);
    }

    @Override
    public ASTNode visitReturnStatement(TinyParser.ReturnStatementContext ctx) {
        ASTNode val = ctx.expression() != null ? visit(ctx.expression()) : null;
        return new ASTNode.ReturnStatement(val);
    }

    @Override
    public ASTNode visitBlock(TinyParser.BlockContext ctx) {
        List<ASTNode> stmts = new ArrayList<>();
        for (TinyParser.StatementContext s : ctx.statement()) {
            stmts.add(visit(s));
        }
        return new ASTNode.Block(stmts);
    }

    @Override
    public ASTNode visitExpression(TinyParser.ExpressionContext ctx) {
        // Parenthesised expression
        if (ctx.LPAREN() != null && ctx.expression().size() == 1) {
            return visit(ctx.expression(0));
        }

        // Unary NOT
        if (ctx.NOT() != null && ctx.expression().size() == 1) {
            return new ASTNode.UnaryOp("!", visit(ctx.expression(0)));
        }

        // Binary operations
        if (ctx.expression().size() == 2) {
            ASTNode left  = visit(ctx.expression(0));
            ASTNode right = visit(ctx.expression(1));
            String op;

            if      (ctx.PLUS()  != null) op = "+";
            else if (ctx.MINUS() != null) op = "-";
            else if (ctx.MULT()  != null) op = "*";
            else if (ctx.DIV()   != null) op = "/";
            else if (ctx.MOD()   != null) op = "%";
            else if (ctx.EQ()    != null) op = "==";
            else if (ctx.NEQ()   != null) op = "!=";
            else if (ctx.LT()    != null) op = "<";
            else if (ctx.GT()    != null) op = ">";
            else if (ctx.LE()    != null) op = "<=";
            else if (ctx.GE()    != null) op = ">=";
            else if (ctx.AND()   != null) op = "&&";
            else if (ctx.OR()    != null) op = "||";
            else throw new RuntimeException("Unknown binary operator in: " + ctx.getText());

            return new ASTNode.BinaryOp(op, left, right);
        }

        // Terminals
        if (ctx.INT()     != null) return new ASTNode.IntLiteral(Integer.parseInt(ctx.INT().getText()));
        if (ctx.FLOAT()   != null) return new ASTNode.FloatLiteral(Double.parseDouble(ctx.FLOAT().getText()));
        if (ctx.STRING()  != null) {
            String raw = ctx.STRING().getText();
            // Strip surrounding quotes
            String val = raw.substring(1, raw.length() - 1);
            return new ASTNode.StringLiteral(val);
        }
        if (ctx.BOOLEAN() != null) return new ASTNode.BoolLiteral(Boolean.parseBoolean(ctx.BOOLEAN().getText()));
        if (ctx.ID()      != null) return new ASTNode.Identifier(ctx.ID().getText());

        throw new RuntimeException("Unrecognised expression: " + ctx.getText());
    }
}
