// Generated from C:/Users/Rawan/Desktop/yarab/CompilerProject/src/TinyParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class TinyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, WHILE=3, RETURN=4, TRUE=5, FALSE=6, PLUS=7, MINUS=8, MULT=9, 
		DIV=10, MOD=11, EQ=12, NEQ=13, LT=14, GT=15, LE=16, GE=17, ASSIGN=18, 
		PLUS_ASSIGN=19, MINUS_ASSIGN=20, MULT_ASSIGN=21, DIV_ASSIGN=22, AND=23, 
		OR=24, NOT=25, LPAREN=26, RPAREN=27, LBRACE=28, RBRACE=29, SEMICOLON=30, 
		COMMA=31, INT=32, FLOAT=33, STRING=34, BOOLEAN=35, ID=36, LINE_COMMENT=37, 
		BLOCK_COMMENT=38, WS=39, Error=40;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_ifStatement = 2, RULE_whileStatement = 3, 
		RULE_assignment = 4, RULE_returnStatement = 5, RULE_block = 6, RULE_expression = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "ifStatement", "whileStatement", "assignment", 
			"returnStatement", "block", "expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'while'", "'return'", "'true'", "'false'", "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", 
			"'='", "'+='", "'-='", "'*='", "'/='", "'&&'", "'||'", "'!'", "'('", 
			"')'", "'{'", "'}'", "';'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "WHILE", "RETURN", "TRUE", "FALSE", "PLUS", "MINUS", 
			"MULT", "DIV", "MOD", "EQ", "NEQ", "LT", "GT", "LE", "GE", "ASSIGN", 
			"PLUS_ASSIGN", "MINUS_ASSIGN", "MULT_ASSIGN", "DIV_ASSIGN", "AND", "OR", 
			"NOT", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "SEMICOLON", "COMMA", 
			"INT", "FLOAT", "STRING", "BOOLEAN", "ID", "LINE_COMMENT", "BLOCK_COMMENT", 
			"WS", "Error"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TinyParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TinyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(TinyParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 68987912218L) != 0)) {
				{
				{
				setState(16);
				statement();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(29);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				ifStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 2);
				{
				setState(25);
				whileStatement();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(26);
				assignment();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 4);
				{
				setState(27);
				returnStatement();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 5);
				{
				setState(28);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(TinyParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(TinyParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TinyParser.RPAREN, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(TinyParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			match(IF);
			setState(32);
			match(LPAREN);
			setState(33);
			expression(0);
			setState(34);
			match(RPAREN);
			setState(35);
			block();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(36);
				match(ELSE);
				setState(37);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(TinyParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(TinyParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TinyParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(WHILE);
			setState(41);
			match(LPAREN);
			setState(42);
			expression(0);
			setState(43);
			match(RPAREN);
			setState(44);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(TinyParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(TinyParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(TinyParser.SEMICOLON, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(ID);
			setState(47);
			match(ASSIGN);
			setState(48);
			expression(0);
			setState(49);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(TinyParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(TinyParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(RETURN);
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 133244649472L) != 0)) {
				{
				setState(52);
				expression(0);
				}
			}

			setState(55);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(TinyParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(TinyParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(LBRACE);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 68987912218L) != 0)) {
				{
				{
				setState(58);
				statement();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(TinyParser.NOT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(TinyParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TinyParser.RPAREN, 0); }
		public TerminalNode INT() { return getToken(TinyParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(TinyParser.FLOAT, 0); }
		public TerminalNode STRING() { return getToken(TinyParser.STRING, 0); }
		public TerminalNode BOOLEAN() { return getToken(TinyParser.BOOLEAN, 0); }
		public TerminalNode ID() { return getToken(TinyParser.ID, 0); }
		public TerminalNode PLUS() { return getToken(TinyParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(TinyParser.MINUS, 0); }
		public TerminalNode MULT() { return getToken(TinyParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(TinyParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(TinyParser.MOD, 0); }
		public TerminalNode EQ() { return getToken(TinyParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(TinyParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(TinyParser.LT, 0); }
		public TerminalNode GT() { return getToken(TinyParser.GT, 0); }
		public TerminalNode LE() { return getToken(TinyParser.LE, 0); }
		public TerminalNode GE() { return getToken(TinyParser.GE, 0); }
		public TerminalNode AND() { return getToken(TinyParser.AND, 0); }
		public TerminalNode OR() { return getToken(TinyParser.OR, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyParserListener ) ((TinyParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyParserVisitor ) return ((TinyParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				{
				setState(67);
				match(NOT);
				setState(68);
				expression(7);
				}
				break;
			case LPAREN:
				{
				setState(69);
				match(LPAREN);
				setState(70);
				expression(0);
				setState(71);
				match(RPAREN);
				}
				break;
			case INT:
				{
				setState(73);
				match(INT);
				}
				break;
			case FLOAT:
				{
				setState(74);
				match(FLOAT);
				}
				break;
			case STRING:
				{
				setState(75);
				match(STRING);
				}
				break;
			case BOOLEAN:
				{
				setState(76);
				match(BOOLEAN);
				}
				break;
			case ID:
				{
				setState(77);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(119);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(80);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(81);
						match(PLUS);
						setState(82);
						expression(21);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(83);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(84);
						match(MINUS);
						setState(85);
						expression(20);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(86);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(87);
						match(MULT);
						setState(88);
						expression(19);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(89);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(90);
						match(DIV);
						setState(91);
						expression(18);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(92);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(93);
						match(MOD);
						setState(94);
						expression(17);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(95);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(96);
						match(EQ);
						setState(97);
						expression(16);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(98);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(99);
						match(NEQ);
						setState(100);
						expression(15);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(101);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(102);
						match(LT);
						setState(103);
						expression(14);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(104);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(105);
						match(GT);
						setState(106);
						expression(13);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(107);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(108);
						match(LE);
						setState(109);
						expression(12);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(110);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(111);
						match(GE);
						setState(112);
						expression(11);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(113);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(114);
						match(AND);
						setState(115);
						expression(10);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(116);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(117);
						match(OR);
						setState(118);
						expression(9);
						}
						break;
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 20);
		case 1:
			return precpred(_ctx, 19);
		case 2:
			return precpred(_ctx, 18);
		case 3:
			return precpred(_ctx, 17);
		case 4:
			return precpred(_ctx, 16);
		case 5:
			return precpred(_ctx, 15);
		case 6:
			return precpred(_ctx, 14);
		case 7:
			return precpred(_ctx, 13);
		case 8:
			return precpred(_ctx, 12);
		case 9:
			return precpred(_ctx, 11);
		case 10:
			return precpred(_ctx, 10);
		case 11:
			return precpred(_ctx, 9);
		case 12:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001(}\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002"+
		"\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005"+
		"\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001\u0000"+
		"\u0005\u0000\u0012\b\u0000\n\u0000\f\u0000\u0015\t\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u0001\u001e\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002\'\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0003"+
		"\u00056\b\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0005"+
		"\u0006<\b\u0006\n\u0006\f\u0006?\t\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003"+
		"\u0007O\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007x\b"+
		"\u0007\n\u0007\f\u0007{\t\u0007\u0001\u0007\u0000\u0001\u000e\b\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0000\u0000\u008f\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0002\u001d\u0001\u0000\u0000\u0000\u0004\u001f\u0001\u0000"+
		"\u0000\u0000\u0006(\u0001\u0000\u0000\u0000\b.\u0001\u0000\u0000\u0000"+
		"\n3\u0001\u0000\u0000\u0000\f9\u0001\u0000\u0000\u0000\u000eN\u0001\u0000"+
		"\u0000\u0000\u0010\u0012\u0003\u0002\u0001\u0000\u0011\u0010\u0001\u0000"+
		"\u0000\u0000\u0012\u0015\u0001\u0000\u0000\u0000\u0013\u0011\u0001\u0000"+
		"\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000\u0014\u0016\u0001\u0000"+
		"\u0000\u0000\u0015\u0013\u0001\u0000\u0000\u0000\u0016\u0017\u0005\u0000"+
		"\u0000\u0001\u0017\u0001\u0001\u0000\u0000\u0000\u0018\u001e\u0003\u0004"+
		"\u0002\u0000\u0019\u001e\u0003\u0006\u0003\u0000\u001a\u001e\u0003\b\u0004"+
		"\u0000\u001b\u001e\u0003\n\u0005\u0000\u001c\u001e\u0003\f\u0006\u0000"+
		"\u001d\u0018\u0001\u0000\u0000\u0000\u001d\u0019\u0001\u0000\u0000\u0000"+
		"\u001d\u001a\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000"+
		"\u001d\u001c\u0001\u0000\u0000\u0000\u001e\u0003\u0001\u0000\u0000\u0000"+
		"\u001f \u0005\u0001\u0000\u0000 !\u0005\u001a\u0000\u0000!\"\u0003\u000e"+
		"\u0007\u0000\"#\u0005\u001b\u0000\u0000#&\u0003\f\u0006\u0000$%\u0005"+
		"\u0002\u0000\u0000%\'\u0003\f\u0006\u0000&$\u0001\u0000\u0000\u0000&\'"+
		"\u0001\u0000\u0000\u0000\'\u0005\u0001\u0000\u0000\u0000()\u0005\u0003"+
		"\u0000\u0000)*\u0005\u001a\u0000\u0000*+\u0003\u000e\u0007\u0000+,\u0005"+
		"\u001b\u0000\u0000,-\u0003\f\u0006\u0000-\u0007\u0001\u0000\u0000\u0000"+
		"./\u0005$\u0000\u0000/0\u0005\u0012\u0000\u000001\u0003\u000e\u0007\u0000"+
		"12\u0005\u001e\u0000\u00002\t\u0001\u0000\u0000\u000035\u0005\u0004\u0000"+
		"\u000046\u0003\u000e\u0007\u000054\u0001\u0000\u0000\u000056\u0001\u0000"+
		"\u0000\u000067\u0001\u0000\u0000\u000078\u0005\u001e\u0000\u00008\u000b"+
		"\u0001\u0000\u0000\u00009=\u0005\u001c\u0000\u0000:<\u0003\u0002\u0001"+
		"\u0000;:\u0001\u0000\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001\u0000"+
		"\u0000\u0000=>\u0001\u0000\u0000\u0000>@\u0001\u0000\u0000\u0000?=\u0001"+
		"\u0000\u0000\u0000@A\u0005\u001d\u0000\u0000A\r\u0001\u0000\u0000\u0000"+
		"BC\u0006\u0007\uffff\uffff\u0000CD\u0005\u0019\u0000\u0000DO\u0003\u000e"+
		"\u0007\u0007EF\u0005\u001a\u0000\u0000FG\u0003\u000e\u0007\u0000GH\u0005"+
		"\u001b\u0000\u0000HO\u0001\u0000\u0000\u0000IO\u0005 \u0000\u0000JO\u0005"+
		"!\u0000\u0000KO\u0005\"\u0000\u0000LO\u0005#\u0000\u0000MO\u0005$\u0000"+
		"\u0000NB\u0001\u0000\u0000\u0000NE\u0001\u0000\u0000\u0000NI\u0001\u0000"+
		"\u0000\u0000NJ\u0001\u0000\u0000\u0000NK\u0001\u0000\u0000\u0000NL\u0001"+
		"\u0000\u0000\u0000NM\u0001\u0000\u0000\u0000Oy\u0001\u0000\u0000\u0000"+
		"PQ\n\u0014\u0000\u0000QR\u0005\u0007\u0000\u0000Rx\u0003\u000e\u0007\u0015"+
		"ST\n\u0013\u0000\u0000TU\u0005\b\u0000\u0000Ux\u0003\u000e\u0007\u0014"+
		"VW\n\u0012\u0000\u0000WX\u0005\t\u0000\u0000Xx\u0003\u000e\u0007\u0013"+
		"YZ\n\u0011\u0000\u0000Z[\u0005\n\u0000\u0000[x\u0003\u000e\u0007\u0012"+
		"\\]\n\u0010\u0000\u0000]^\u0005\u000b\u0000\u0000^x\u0003\u000e\u0007"+
		"\u0011_`\n\u000f\u0000\u0000`a\u0005\f\u0000\u0000ax\u0003\u000e\u0007"+
		"\u0010bc\n\u000e\u0000\u0000cd\u0005\r\u0000\u0000dx\u0003\u000e\u0007"+
		"\u000fef\n\r\u0000\u0000fg\u0005\u000e\u0000\u0000gx\u0003\u000e\u0007"+
		"\u000ehi\n\f\u0000\u0000ij\u0005\u000f\u0000\u0000jx\u0003\u000e\u0007"+
		"\rkl\n\u000b\u0000\u0000lm\u0005\u0010\u0000\u0000mx\u0003\u000e\u0007"+
		"\fno\n\n\u0000\u0000op\u0005\u0011\u0000\u0000px\u0003\u000e\u0007\u000b"+
		"qr\n\t\u0000\u0000rs\u0005\u0017\u0000\u0000sx\u0003\u000e\u0007\ntu\n"+
		"\b\u0000\u0000uv\u0005\u0018\u0000\u0000vx\u0003\u000e\u0007\twP\u0001"+
		"\u0000\u0000\u0000wS\u0001\u0000\u0000\u0000wV\u0001\u0000\u0000\u0000"+
		"wY\u0001\u0000\u0000\u0000w\\\u0001\u0000\u0000\u0000w_\u0001\u0000\u0000"+
		"\u0000wb\u0001\u0000\u0000\u0000we\u0001\u0000\u0000\u0000wh\u0001\u0000"+
		"\u0000\u0000wk\u0001\u0000\u0000\u0000wn\u0001\u0000\u0000\u0000wq\u0001"+
		"\u0000\u0000\u0000wt\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000"+
		"yw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z\u000f\u0001\u0000"+
		"\u0000\u0000{y\u0001\u0000\u0000\u0000\b\u0013\u001d&5=Nwy";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}