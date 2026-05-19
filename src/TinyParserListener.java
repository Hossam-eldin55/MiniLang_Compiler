// Generated from C:/Users/Rawan/Desktop/yarab/CompilerProject/src/TinyParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TinyParser}.
 */
public interface TinyParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TinyParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(TinyParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(TinyParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(TinyParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(TinyParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(TinyParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(TinyParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(TinyParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(TinyParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(TinyParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(TinyParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(TinyParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(TinyParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TinyParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TinyParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(TinyParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(TinyParser.ExpressionContext ctx);
}