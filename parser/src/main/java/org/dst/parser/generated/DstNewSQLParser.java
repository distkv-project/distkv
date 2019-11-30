// Generated from DstNewSQL.g4 by ANTLR 4.7

package org.dst.parser.generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DstNewSQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, NON_NEGATIVE_INT=24, 
		STRING=25, WS=26;
	public static final int
		RULE_statement = 0, RULE_conceptStatement = 1, RULE_strStatement = 2, 
		RULE_strPut = 3, RULE_strGet = 4, RULE_listStatement = 5, RULE_listPut = 6, 
		RULE_listLput = 7, RULE_listRput = 8, RULE_listGet = 9, RULE_listRGet = 10, 
		RULE_listDelete = 11, RULE_listMDelete = 12, RULE_listGetArguments = 13, 
		RULE_listGetAll = 14, RULE_listGetOne = 15, RULE_listGetRange = 16, RULE_listRemoveOne = 17, 
		RULE_listRemoveRange = 18, RULE_setStatement = 19, RULE_setPut = 20, RULE_setGet = 21, 
		RULE_setPutItem = 22, RULE_setRemoveItem = 23, RULE_setExists = 24, RULE_setDrop = 25, 
		RULE_dictStatement = 26, RULE_dictPut = 27, RULE_dictGet = 28, RULE_dictPutItem = 29, 
		RULE_dictGetItem = 30, RULE_dictPopItem = 31, RULE_dictRemoveItem = 32, 
		RULE_dictDrop = 33, RULE_keyValuePairs = 34, RULE_keyValuePair = 35, RULE_itemKey = 36, 
		RULE_itemValue = 37, RULE_key = 38, RULE_value = 39, RULE_valueArray = 40, 
		RULE_index = 41;
	public static final String[] ruleNames = {
		"statement", "conceptStatement", "strStatement", "strPut", "strGet", "listStatement", 
		"listPut", "listLput", "listRput", "listGet", "listRGet", "listDelete", 
		"listMDelete", "listGetArguments", "listGetAll", "listGetOne", "listGetRange", 
		"listRemoveOne", "listRemoveRange", "setStatement", "setPut", "setGet", 
		"setPutItem", "setRemoveItem", "setExists", "setDrop", "dictStatement", 
		"dictPut", "dictGet", "dictPutItem", "dictGetItem", "dictPopItem", "dictRemoveItem", 
		"dictDrop", "keyValuePairs", "keyValuePair", "itemKey", "itemValue", "key", 
		"value", "valueArray", "index"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'str.put'", "'str.get'", "'list.put'", "'list.lput'", "'list.rput'", 
		"'list.get'", "'list.rget'", "'list.remove'", "'list.mRemove'", "'set.put'", 
		"'set.get'", "'set.putItem'", "'set.remove'", "'set.removeItem'", "'set.exists'", 
		"'set.drop'", "'dict.put'", "'dict.get'", "'dict.putItem'", "'dict.getItem'", 
		"'dict.popItem'", "'dict.removeItem'", "'dict.drop'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"NON_NEGATIVE_INT", "STRING", "WS"
	};
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
	public String getGrammarFileName() { return "DstNewSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DstNewSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StatementContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(DstNewSQLParser.EOF, 0); }
		public ConceptStatementContext conceptStatement() {
			return getRuleContext(ConceptStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(84);
			conceptStatement();
			}
			setState(85);
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

	public static class ConceptStatementContext extends ParserRuleContext {
		public StrStatementContext strStatement() {
			return getRuleContext(StrStatementContext.class,0);
		}
		public ListStatementContext listStatement() {
			return getRuleContext(ListStatementContext.class,0);
		}
		public SetStatementContext setStatement() {
			return getRuleContext(SetStatementContext.class,0);
		}
		public DictStatementContext dictStatement() {
			return getRuleContext(DictStatementContext.class,0);
		}
		public ConceptStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conceptStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterConceptStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitConceptStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitConceptStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConceptStatementContext conceptStatement() throws RecognitionException {
		ConceptStatementContext _localctx = new ConceptStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_conceptStatement);
		try {
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				strStatement();
				}
				break;
			case T__2:
			case T__3:
			case T__4:
			case T__5:
			case T__6:
			case T__7:
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(88);
				listStatement();
				}
				break;
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				setStatement();
				}
				break;
			case T__16:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
				dictStatement();
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

	public static class StrStatementContext extends ParserRuleContext {
		public StrPutContext strPut() {
			return getRuleContext(StrPutContext.class,0);
		}
		public StrGetContext strGet() {
			return getRuleContext(StrGetContext.class,0);
		}
		public StrStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterStrStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitStrStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitStrStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrStatementContext strStatement() throws RecognitionException {
		StrStatementContext _localctx = new StrStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_strStatement);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				strPut();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				strGet();
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

	public static class StrPutContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public StrPutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strPut; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterStrPut(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitStrPut(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitStrPut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrPutContext strPut() throws RecognitionException {
		StrPutContext _localctx = new StrPutContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_strPut);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__0);
			setState(98);
			key();
			setState(99);
			value();
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

	public static class StrGetContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public StrGetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strGet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterStrGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitStrGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitStrGet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrGetContext strGet() throws RecognitionException {
		StrGetContext _localctx = new StrGetContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_strGet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(T__1);
			setState(102);
			key();
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

	public static class ListStatementContext extends ParserRuleContext {
		public ListPutContext listPut() {
			return getRuleContext(ListPutContext.class,0);
		}
		public ListLputContext listLput() {
			return getRuleContext(ListLputContext.class,0);
		}
		public ListRputContext listRput() {
			return getRuleContext(ListRputContext.class,0);
		}
		public ListGetContext listGet() {
			return getRuleContext(ListGetContext.class,0);
		}
		public ListRGetContext listRGet() {
			return getRuleContext(ListRGetContext.class,0);
		}
		public ListDeleteContext listDelete() {
			return getRuleContext(ListDeleteContext.class,0);
		}
		public ListMDeleteContext listMDelete() {
			return getRuleContext(ListMDeleteContext.class,0);
		}
		public ListStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListStatementContext listStatement() throws RecognitionException {
		ListStatementContext _localctx = new ListStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_listStatement);
		try {
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				listPut();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				listLput();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(106);
				listRput();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 4);
				{
				setState(107);
				listGet();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 5);
				{
				setState(108);
				listRGet();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 6);
				{
				setState(109);
				listDelete();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 7);
				{
				setState(110);
				listMDelete();
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

	public static class ListPutContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ValueArrayContext valueArray() {
			return getRuleContext(ValueArrayContext.class,0);
		}
		public ListPutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listPut; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListPut(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListPut(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListPut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListPutContext listPut() throws RecognitionException {
		ListPutContext _localctx = new ListPutContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_listPut);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(T__2);
			setState(114);
			key();
			setState(115);
			valueArray();
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

	public static class ListLputContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ValueArrayContext valueArray() {
			return getRuleContext(ValueArrayContext.class,0);
		}
		public ListLputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listLput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListLput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListLput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListLput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListLputContext listLput() throws RecognitionException {
		ListLputContext _localctx = new ListLputContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_listLput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(T__3);
			setState(118);
			key();
			setState(119);
			valueArray();
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

	public static class ListRputContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ValueArrayContext valueArray() {
			return getRuleContext(ValueArrayContext.class,0);
		}
		public ListRputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listRput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListRput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListRput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListRput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListRputContext listRput() throws RecognitionException {
		ListRputContext _localctx = new ListRputContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_listRput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(T__4);
			setState(122);
			key();
			setState(123);
			valueArray();
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

	public static class ListGetContext extends ParserRuleContext {
		public ListGetAllContext listGetAll() {
			return getRuleContext(ListGetAllContext.class,0);
		}
		public ListGetOneContext listGetOne() {
			return getRuleContext(ListGetOneContext.class,0);
		}
		public ListGetRangeContext listGetRange() {
			return getRuleContext(ListGetRangeContext.class,0);
		}
		public ListGetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listGet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListGet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListGetContext listGet() throws RecognitionException {
		ListGetContext _localctx = new ListGetContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_listGet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(T__5);
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(126);
				listGetAll();
				}
				break;
			case 2:
				{
				setState(127);
				listGetOne();
				}
				break;
			case 3:
				{
				setState(128);
				listGetRange();
				}
				break;
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

	public static class ListRGetContext extends ParserRuleContext {
		public ListGetArgumentsContext listGetArguments() {
			return getRuleContext(ListGetArgumentsContext.class,0);
		}
		public ListRGetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listRGet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListRGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListRGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListRGet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListRGetContext listRGet() throws RecognitionException {
		ListRGetContext _localctx = new ListRGetContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_listRGet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(T__6);
			setState(132);
			listGetArguments();
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

	public static class ListDeleteContext extends ParserRuleContext {
		public ListRemoveOneContext listRemoveOne() {
			return getRuleContext(ListRemoveOneContext.class,0);
		}
		public ListRemoveRangeContext listRemoveRange() {
			return getRuleContext(ListRemoveRangeContext.class,0);
		}
		public ListDeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listDelete; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListDelete(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListDelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListDeleteContext listDelete() throws RecognitionException {
		ListDeleteContext _localctx = new ListDeleteContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_listDelete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(T__7);
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(135);
				listRemoveOne();
				}
				break;
			case 2:
				{
				setState(136);
				listRemoveRange();
				}
				break;
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

	public static class ListMDeleteContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public ListMDeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listMDelete; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListMDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListMDelete(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListMDelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListMDeleteContext listMDelete() throws RecognitionException {
		ListMDeleteContext _localctx = new ListMDeleteContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_listMDelete);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(T__8);
			setState(140);
			key();
			setState(142); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(141);
				index();
				}
				}
				setState(144); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NON_NEGATIVE_INT );
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

	public static class ListGetArgumentsContext extends ParserRuleContext {
		public ListGetAllContext listGetAll() {
			return getRuleContext(ListGetAllContext.class,0);
		}
		public ListGetOneContext listGetOne() {
			return getRuleContext(ListGetOneContext.class,0);
		}
		public ListGetRangeContext listGetRange() {
			return getRuleContext(ListGetRangeContext.class,0);
		}
		public ListGetArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listGetArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListGetArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListGetArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListGetArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListGetArgumentsContext listGetArguments() throws RecognitionException {
		ListGetArgumentsContext _localctx = new ListGetArgumentsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_listGetArguments);
		try {
			setState(149);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				listGetAll();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(147);
				listGetOne();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(148);
				listGetRange();
				}
				break;
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

	public static class ListGetAllContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ListGetAllContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listGetAll; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListGetAll(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListGetAll(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListGetAll(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListGetAllContext listGetAll() throws RecognitionException {
		ListGetAllContext _localctx = new ListGetAllContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_listGetAll);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			key();
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

	public static class ListGetOneContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public ListGetOneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listGetOne; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListGetOne(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListGetOne(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListGetOne(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListGetOneContext listGetOne() throws RecognitionException {
		ListGetOneContext _localctx = new ListGetOneContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_listGetOne);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			key();
			setState(154);
			index();
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

	public static class ListGetRangeContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public ListGetRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listGetRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListGetRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListGetRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListGetRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListGetRangeContext listGetRange() throws RecognitionException {
		ListGetRangeContext _localctx = new ListGetRangeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_listGetRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			key();
			setState(157);
			index();
			setState(158);
			index();
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

	public static class ListRemoveOneContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public ListRemoveOneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listRemoveOne; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListRemoveOne(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListRemoveOne(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListRemoveOne(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListRemoveOneContext listRemoveOne() throws RecognitionException {
		ListRemoveOneContext _localctx = new ListRemoveOneContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_listRemoveOne);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			key();
			setState(161);
			index();
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

	public static class ListRemoveRangeContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public ListRemoveRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listRemoveRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterListRemoveRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitListRemoveRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitListRemoveRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListRemoveRangeContext listRemoveRange() throws RecognitionException {
		ListRemoveRangeContext _localctx = new ListRemoveRangeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_listRemoveRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			key();
			setState(164);
			index();
			setState(165);
			index();
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

	public static class SetStatementContext extends ParserRuleContext {
		public SetPutContext setPut() {
			return getRuleContext(SetPutContext.class,0);
		}
		public SetGetContext setGet() {
			return getRuleContext(SetGetContext.class,0);
		}
		public SetPutItemContext setPutItem() {
			return getRuleContext(SetPutItemContext.class,0);
		}
		public SetRemoveItemContext setRemoveItem() {
			return getRuleContext(SetRemoveItemContext.class,0);
		}
		public SetExistsContext setExists() {
			return getRuleContext(SetExistsContext.class,0);
		}
		public SetDropContext setDrop() {
			return getRuleContext(SetDropContext.class,0);
		}
		public SetStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetStatementContext setStatement() throws RecognitionException {
		SetStatementContext _localctx = new SetStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_setStatement);
		try {
			setState(173);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(167);
				setPut();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(168);
				setGet();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(169);
				setPutItem();
				}
				break;
			case T__12:
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(170);
				setRemoveItem();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 5);
				{
				setState(171);
				setExists();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 6);
				{
				setState(172);
				setDrop();
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

	public static class SetPutContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ValueArrayContext valueArray() {
			return getRuleContext(ValueArrayContext.class,0);
		}
		public SetPutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setPut; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetPut(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetPut(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetPut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetPutContext setPut() throws RecognitionException {
		SetPutContext _localctx = new SetPutContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_setPut);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(T__9);
			setState(176);
			key();
			setState(177);
			valueArray();
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

	public static class SetGetContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public SetGetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setGet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetGet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetGetContext setGet() throws RecognitionException {
		SetGetContext _localctx = new SetGetContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_setGet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(T__10);
			setState(180);
			key();
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

	public static class SetPutItemContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemValueContext itemValue() {
			return getRuleContext(ItemValueContext.class,0);
		}
		public SetPutItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setPutItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetPutItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetPutItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetPutItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetPutItemContext setPutItem() throws RecognitionException {
		SetPutItemContext _localctx = new SetPutItemContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_setPutItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(T__11);
			setState(183);
			key();
			setState(184);
			itemValue();
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

	public static class SetRemoveItemContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemValueContext itemValue() {
			return getRuleContext(ItemValueContext.class,0);
		}
		public SetRemoveItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setRemoveItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetRemoveItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetRemoveItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetRemoveItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetRemoveItemContext setRemoveItem() throws RecognitionException {
		SetRemoveItemContext _localctx = new SetRemoveItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_setRemoveItem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			_la = _input.LA(1);
			if ( !(_la==T__12 || _la==T__13) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(187);
			key();
			setState(188);
			itemValue();
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

	public static class SetExistsContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemValueContext itemValue() {
			return getRuleContext(ItemValueContext.class,0);
		}
		public SetExistsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setExists; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetExists(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetExists(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetExistsContext setExists() throws RecognitionException {
		SetExistsContext _localctx = new SetExistsContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_setExists);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(T__14);
			setState(191);
			key();
			setState(192);
			itemValue();
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

	public static class SetDropContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public SetDropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setDrop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterSetDrop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitSetDrop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitSetDrop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetDropContext setDrop() throws RecognitionException {
		SetDropContext _localctx = new SetDropContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_setDrop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__15);
			setState(195);
			key();
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

	public static class DictStatementContext extends ParserRuleContext {
		public DictPutContext dictPut() {
			return getRuleContext(DictPutContext.class,0);
		}
		public DictGetContext dictGet() {
			return getRuleContext(DictGetContext.class,0);
		}
		public DictPutItemContext dictPutItem() {
			return getRuleContext(DictPutItemContext.class,0);
		}
		public DictGetItemContext dictGetItem() {
			return getRuleContext(DictGetItemContext.class,0);
		}
		public DictPopItemContext dictPopItem() {
			return getRuleContext(DictPopItemContext.class,0);
		}
		public DictRemoveItemContext dictRemoveItem() {
			return getRuleContext(DictRemoveItemContext.class,0);
		}
		public DictDropContext dictDrop() {
			return getRuleContext(DictDropContext.class,0);
		}
		public DictStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictStatementContext dictStatement() throws RecognitionException {
		DictStatementContext _localctx = new DictStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_dictStatement);
		try {
			setState(204);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(197);
				dictPut();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(198);
				dictGet();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 3);
				{
				setState(199);
				dictPutItem();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 4);
				{
				setState(200);
				dictGetItem();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 5);
				{
				setState(201);
				dictPopItem();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 6);
				{
				setState(202);
				dictRemoveItem();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 7);
				{
				setState(203);
				dictDrop();
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

	public static class DictPutContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public KeyValuePairsContext keyValuePairs() {
			return getRuleContext(KeyValuePairsContext.class,0);
		}
		public DictPutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictPut; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictPut(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictPut(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictPut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictPutContext dictPut() throws RecognitionException {
		DictPutContext _localctx = new DictPutContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_dictPut);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(T__16);
			setState(207);
			key();
			setState(208);
			keyValuePairs();
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

	public static class DictGetContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public DictGetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictGet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictGet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictGetContext dictGet() throws RecognitionException {
		DictGetContext _localctx = new DictGetContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_dictGet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(T__17);
			setState(211);
			key();
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

	public static class DictPutItemContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemKeyContext itemKey() {
			return getRuleContext(ItemKeyContext.class,0);
		}
		public ItemValueContext itemValue() {
			return getRuleContext(ItemValueContext.class,0);
		}
		public DictPutItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictPutItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictPutItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictPutItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictPutItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictPutItemContext dictPutItem() throws RecognitionException {
		DictPutItemContext _localctx = new DictPutItemContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_dictPutItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(T__18);
			setState(214);
			key();
			setState(215);
			itemKey();
			setState(216);
			itemValue();
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

	public static class DictGetItemContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemKeyContext itemKey() {
			return getRuleContext(ItemKeyContext.class,0);
		}
		public DictGetItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictGetItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictGetItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictGetItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictGetItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictGetItemContext dictGetItem() throws RecognitionException {
		DictGetItemContext _localctx = new DictGetItemContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_dictGetItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(T__19);
			setState(219);
			key();
			setState(220);
			itemKey();
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

	public static class DictPopItemContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemKeyContext itemKey() {
			return getRuleContext(ItemKeyContext.class,0);
		}
		public DictPopItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictPopItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictPopItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictPopItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictPopItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictPopItemContext dictPopItem() throws RecognitionException {
		DictPopItemContext _localctx = new DictPopItemContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_dictPopItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(T__20);
			setState(223);
			key();
			setState(224);
			itemKey();
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

	public static class DictRemoveItemContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ItemKeyContext itemKey() {
			return getRuleContext(ItemKeyContext.class,0);
		}
		public DictRemoveItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictRemoveItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictRemoveItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictRemoveItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictRemoveItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictRemoveItemContext dictRemoveItem() throws RecognitionException {
		DictRemoveItemContext _localctx = new DictRemoveItemContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_dictRemoveItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(T__21);
			setState(227);
			key();
			setState(228);
			itemKey();
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

	public static class DictDropContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public DictDropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictDrop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterDictDrop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitDictDrop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitDictDrop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictDropContext dictDrop() throws RecognitionException {
		DictDropContext _localctx = new DictDropContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_dictDrop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(T__22);
			setState(231);
			key();
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

	public static class KeyValuePairsContext extends ParserRuleContext {
		public List<KeyValuePairContext> keyValuePair() {
			return getRuleContexts(KeyValuePairContext.class);
		}
		public KeyValuePairContext keyValuePair(int i) {
			return getRuleContext(KeyValuePairContext.class,i);
		}
		public KeyValuePairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyValuePairs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterKeyValuePairs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitKeyValuePairs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitKeyValuePairs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyValuePairsContext keyValuePairs() throws RecognitionException {
		KeyValuePairsContext _localctx = new KeyValuePairsContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_keyValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(233);
				keyValuePair();
				}
				}
				setState(236); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==STRING );
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

	public static class KeyValuePairContext extends ParserRuleContext {
		public ItemKeyContext itemKey() {
			return getRuleContext(ItemKeyContext.class,0);
		}
		public ItemValueContext itemValue() {
			return getRuleContext(ItemValueContext.class,0);
		}
		public KeyValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyValuePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterKeyValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitKeyValuePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitKeyValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyValuePairContext keyValuePair() throws RecognitionException {
		KeyValuePairContext _localctx = new KeyValuePairContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_keyValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			itemKey();
			setState(239);
			itemValue();
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

	public static class ItemKeyContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(DstNewSQLParser.STRING, 0); }
		public ItemKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_itemKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterItemKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitItemKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitItemKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItemKeyContext itemKey() throws RecognitionException {
		ItemKeyContext _localctx = new ItemKeyContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_itemKey);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(STRING);
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

	public static class ItemValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(DstNewSQLParser.STRING, 0); }
		public ItemValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_itemValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterItemValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitItemValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitItemValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItemValueContext itemValue() throws RecognitionException {
		ItemValueContext _localctx = new ItemValueContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_itemValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(STRING);
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

	public static class KeyContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(DstNewSQLParser.STRING, 0); }
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_key);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(STRING);
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(DstNewSQLParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(STRING);
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

	public static class ValueArrayContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(DstNewSQLParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(DstNewSQLParser.STRING, i);
		}
		public ValueArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueArray; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterValueArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitValueArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitValueArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueArrayContext valueArray() throws RecognitionException {
		ValueArrayContext _localctx = new ValueArrayContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_valueArray);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(249);
				match(STRING);
				}
				}
				setState(252); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==STRING );
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

	public static class IndexContext extends ParserRuleContext {
		public TerminalNode NON_NEGATIVE_INT() { return getToken(DstNewSQLParser.NON_NEGATIVE_INT, 0); }
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).enterIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DstNewSQLListener ) ((DstNewSQLListener)listener).exitIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DstNewSQLVisitor ) return ((DstNewSQLVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(NON_NEGATIVE_INT);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\34\u0103\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3^\n\3\3\4\3\4\5\4b\n\4\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7r\n\7\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\5\13\u0084\n\13\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\5\r\u008c\n\r\3\16\3\16\3\16\6\16\u0091\n\16\r\16\16"+
		"\16\u0092\3\17\3\17\3\17\5\17\u0098\n\17\3\20\3\20\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u00b0\n\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u00cf\n\34\3\35\3\35\3\35\3\35\3\36"+
		"\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3"+
		"\"\3\"\3#\3#\3#\3$\6$\u00ed\n$\r$\16$\u00ee\3%\3%\3%\3&\3&\3\'\3\'\3("+
		"\3(\3)\3)\3*\6*\u00fd\n*\r*\16*\u00fe\3+\3+\3+\2\2,\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRT\2\3\3\2\17\20"+
		"\2\u00f5\2V\3\2\2\2\4]\3\2\2\2\6a\3\2\2\2\bc\3\2\2\2\ng\3\2\2\2\fq\3\2"+
		"\2\2\16s\3\2\2\2\20w\3\2\2\2\22{\3\2\2\2\24\177\3\2\2\2\26\u0085\3\2\2"+
		"\2\30\u0088\3\2\2\2\32\u008d\3\2\2\2\34\u0097\3\2\2\2\36\u0099\3\2\2\2"+
		" \u009b\3\2\2\2\"\u009e\3\2\2\2$\u00a2\3\2\2\2&\u00a5\3\2\2\2(\u00af\3"+
		"\2\2\2*\u00b1\3\2\2\2,\u00b5\3\2\2\2.\u00b8\3\2\2\2\60\u00bc\3\2\2\2\62"+
		"\u00c0\3\2\2\2\64\u00c4\3\2\2\2\66\u00ce\3\2\2\28\u00d0\3\2\2\2:\u00d4"+
		"\3\2\2\2<\u00d7\3\2\2\2>\u00dc\3\2\2\2@\u00e0\3\2\2\2B\u00e4\3\2\2\2D"+
		"\u00e8\3\2\2\2F\u00ec\3\2\2\2H\u00f0\3\2\2\2J\u00f3\3\2\2\2L\u00f5\3\2"+
		"\2\2N\u00f7\3\2\2\2P\u00f9\3\2\2\2R\u00fc\3\2\2\2T\u0100\3\2\2\2VW\5\4"+
		"\3\2WX\7\2\2\3X\3\3\2\2\2Y^\5\6\4\2Z^\5\f\7\2[^\5(\25\2\\^\5\66\34\2]"+
		"Y\3\2\2\2]Z\3\2\2\2][\3\2\2\2]\\\3\2\2\2^\5\3\2\2\2_b\5\b\5\2`b\5\n\6"+
		"\2a_\3\2\2\2a`\3\2\2\2b\7\3\2\2\2cd\7\3\2\2de\5N(\2ef\5P)\2f\t\3\2\2\2"+
		"gh\7\4\2\2hi\5N(\2i\13\3\2\2\2jr\5\16\b\2kr\5\20\t\2lr\5\22\n\2mr\5\24"+
		"\13\2nr\5\26\f\2or\5\30\r\2pr\5\32\16\2qj\3\2\2\2qk\3\2\2\2ql\3\2\2\2"+
		"qm\3\2\2\2qn\3\2\2\2qo\3\2\2\2qp\3\2\2\2r\r\3\2\2\2st\7\5\2\2tu\5N(\2"+
		"uv\5R*\2v\17\3\2\2\2wx\7\6\2\2xy\5N(\2yz\5R*\2z\21\3\2\2\2{|\7\7\2\2|"+
		"}\5N(\2}~\5R*\2~\23\3\2\2\2\177\u0083\7\b\2\2\u0080\u0084\5\36\20\2\u0081"+
		"\u0084\5 \21\2\u0082\u0084\5\"\22\2\u0083\u0080\3\2\2\2\u0083\u0081\3"+
		"\2\2\2\u0083\u0082\3\2\2\2\u0084\25\3\2\2\2\u0085\u0086\7\t\2\2\u0086"+
		"\u0087\5\34\17\2\u0087\27\3\2\2\2\u0088\u008b\7\n\2\2\u0089\u008c\5$\23"+
		"\2\u008a\u008c\5&\24\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c\31"+
		"\3\2\2\2\u008d\u008e\7\13\2\2\u008e\u0090\5N(\2\u008f\u0091\5T+\2\u0090"+
		"\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2"+
		"\2\2\u0093\33\3\2\2\2\u0094\u0098\5\36\20\2\u0095\u0098\5 \21\2\u0096"+
		"\u0098\5\"\22\2\u0097\u0094\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0096\3"+
		"\2\2\2\u0098\35\3\2\2\2\u0099\u009a\5N(\2\u009a\37\3\2\2\2\u009b\u009c"+
		"\5N(\2\u009c\u009d\5T+\2\u009d!\3\2\2\2\u009e\u009f\5N(\2\u009f\u00a0"+
		"\5T+\2\u00a0\u00a1\5T+\2\u00a1#\3\2\2\2\u00a2\u00a3\5N(\2\u00a3\u00a4"+
		"\5T+\2\u00a4%\3\2\2\2\u00a5\u00a6\5N(\2\u00a6\u00a7\5T+\2\u00a7\u00a8"+
		"\5T+\2\u00a8\'\3\2\2\2\u00a9\u00b0\5*\26\2\u00aa\u00b0\5,\27\2\u00ab\u00b0"+
		"\5.\30\2\u00ac\u00b0\5\60\31\2\u00ad\u00b0\5\62\32\2\u00ae\u00b0\5\64"+
		"\33\2\u00af\u00a9\3\2\2\2\u00af\u00aa\3\2\2\2\u00af\u00ab\3\2\2\2\u00af"+
		"\u00ac\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00ae\3\2\2\2\u00b0)\3\2\2\2"+
		"\u00b1\u00b2\7\f\2\2\u00b2\u00b3\5N(\2\u00b3\u00b4\5R*\2\u00b4+\3\2\2"+
		"\2\u00b5\u00b6\7\r\2\2\u00b6\u00b7\5N(\2\u00b7-\3\2\2\2\u00b8\u00b9\7"+
		"\16\2\2\u00b9\u00ba\5N(\2\u00ba\u00bb\5L\'\2\u00bb/\3\2\2\2\u00bc\u00bd"+
		"\t\2\2\2\u00bd\u00be\5N(\2\u00be\u00bf\5L\'\2\u00bf\61\3\2\2\2\u00c0\u00c1"+
		"\7\21\2\2\u00c1\u00c2\5N(\2\u00c2\u00c3\5L\'\2\u00c3\63\3\2\2\2\u00c4"+
		"\u00c5\7\22\2\2\u00c5\u00c6\5N(\2\u00c6\65\3\2\2\2\u00c7\u00cf\58\35\2"+
		"\u00c8\u00cf\5:\36\2\u00c9\u00cf\5<\37\2\u00ca\u00cf\5> \2\u00cb\u00cf"+
		"\5@!\2\u00cc\u00cf\5B\"\2\u00cd\u00cf\5D#\2\u00ce\u00c7\3\2\2\2\u00ce"+
		"\u00c8\3\2\2\2\u00ce\u00c9\3\2\2\2\u00ce\u00ca\3\2\2\2\u00ce\u00cb\3\2"+
		"\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\67\3\2\2\2\u00d0\u00d1"+
		"\7\23\2\2\u00d1\u00d2\5N(\2\u00d2\u00d3\5F$\2\u00d39\3\2\2\2\u00d4\u00d5"+
		"\7\24\2\2\u00d5\u00d6\5N(\2\u00d6;\3\2\2\2\u00d7\u00d8\7\25\2\2\u00d8"+
		"\u00d9\5N(\2\u00d9\u00da\5J&\2\u00da\u00db\5L\'\2\u00db=\3\2\2\2\u00dc"+
		"\u00dd\7\26\2\2\u00dd\u00de\5N(\2\u00de\u00df\5J&\2\u00df?\3\2\2\2\u00e0"+
		"\u00e1\7\27\2\2\u00e1\u00e2\5N(\2\u00e2\u00e3\5J&\2\u00e3A\3\2\2\2\u00e4"+
		"\u00e5\7\30\2\2\u00e5\u00e6\5N(\2\u00e6\u00e7\5J&\2\u00e7C\3\2\2\2\u00e8"+
		"\u00e9\7\31\2\2\u00e9\u00ea\5N(\2\u00eaE\3\2\2\2\u00eb\u00ed\5H%\2\u00ec"+
		"\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2"+
		"\2\2\u00efG\3\2\2\2\u00f0\u00f1\5J&\2\u00f1\u00f2\5L\'\2\u00f2I\3\2\2"+
		"\2\u00f3\u00f4\7\33\2\2\u00f4K\3\2\2\2\u00f5\u00f6\7\33\2\2\u00f6M\3\2"+
		"\2\2\u00f7\u00f8\7\33\2\2\u00f8O\3\2\2\2\u00f9\u00fa\7\33\2\2\u00faQ\3"+
		"\2\2\2\u00fb\u00fd\7\33\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe"+
		"\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ffS\3\2\2\2\u0100\u0101\7\32\2\2"+
		"\u0101U\3\2\2\2\r]aq\u0083\u008b\u0092\u0097\u00af\u00ce\u00ee\u00fe";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}