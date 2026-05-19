// Generated from C:/Users/Rawan/Desktop/yarab/CompilerProject/src/TinyParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TinyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TinyParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TinyParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(TinyParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(TinyParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(TinyParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(TinyParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(TinyParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(TinyParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(TinyParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(TinyParser.ExpressionContext ctx);
}