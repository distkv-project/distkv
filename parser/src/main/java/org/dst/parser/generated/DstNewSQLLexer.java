// Generated from DstNewSQL.g4 by ANTLR 4.7

package org.dst.parser.generated;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DstNewSQLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, NON_NEGATIVE_INT=24, 
		STRING=25, WS=26;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "NON_NEGATIVE_INT", 
		"STRING", "WS"
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


	public DstNewSQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DstNewSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\34\u0143\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\7\31\u0130\n\31\f\31\16\31\u0133"+
		"\13\31\3\31\5\31\u0136\n\31\3\32\6\32\u0139\n\32\r\32\16\32\u013a\3\33"+
		"\6\33\u013e\n\33\r\33\16\33\u013f\3\33\3\33\2\2\34\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\3\2\5\3\2\63;\3\2\62;\5\2\13\f\17"+
		"\17\"\"\2\u0146\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\3\67\3\2\2\2\5"+
		"?\3\2\2\2\7G\3\2\2\2\tP\3\2\2\2\13Z\3\2\2\2\rd\3\2\2\2\17m\3\2\2\2\21"+
		"w\3\2\2\2\23\u0083\3\2\2\2\25\u0090\3\2\2\2\27\u0098\3\2\2\2\31\u00a0"+
		"\3\2\2\2\33\u00ac\3\2\2\2\35\u00b7\3\2\2\2\37\u00c6\3\2\2\2!\u00d1\3\2"+
		"\2\2#\u00da\3\2\2\2%\u00e3\3\2\2\2\'\u00ec\3\2\2\2)\u00f9\3\2\2\2+\u0106"+
		"\3\2\2\2-\u0113\3\2\2\2/\u0123\3\2\2\2\61\u0135\3\2\2\2\63\u0138\3\2\2"+
		"\2\65\u013d\3\2\2\2\678\7u\2\289\7v\2\29:\7t\2\2:;\7\60\2\2;<\7r\2\2<"+
		"=\7w\2\2=>\7v\2\2>\4\3\2\2\2?@\7u\2\2@A\7v\2\2AB\7t\2\2BC\7\60\2\2CD\7"+
		"i\2\2DE\7g\2\2EF\7v\2\2F\6\3\2\2\2GH\7n\2\2HI\7k\2\2IJ\7u\2\2JK\7v\2\2"+
		"KL\7\60\2\2LM\7r\2\2MN\7w\2\2NO\7v\2\2O\b\3\2\2\2PQ\7n\2\2QR\7k\2\2RS"+
		"\7u\2\2ST\7v\2\2TU\7\60\2\2UV\7n\2\2VW\7r\2\2WX\7w\2\2XY\7v\2\2Y\n\3\2"+
		"\2\2Z[\7n\2\2[\\\7k\2\2\\]\7u\2\2]^\7v\2\2^_\7\60\2\2_`\7t\2\2`a\7r\2"+
		"\2ab\7w\2\2bc\7v\2\2c\f\3\2\2\2de\7n\2\2ef\7k\2\2fg\7u\2\2gh\7v\2\2hi"+
		"\7\60\2\2ij\7i\2\2jk\7g\2\2kl\7v\2\2l\16\3\2\2\2mn\7n\2\2no\7k\2\2op\7"+
		"u\2\2pq\7v\2\2qr\7\60\2\2rs\7t\2\2st\7i\2\2tu\7g\2\2uv\7v\2\2v\20\3\2"+
		"\2\2wx\7n\2\2xy\7k\2\2yz\7u\2\2z{\7v\2\2{|\7\60\2\2|}\7t\2\2}~\7g\2\2"+
		"~\177\7o\2\2\177\u0080\7q\2\2\u0080\u0081\7x\2\2\u0081\u0082\7g\2\2\u0082"+
		"\22\3\2\2\2\u0083\u0084\7n\2\2\u0084\u0085\7k\2\2\u0085\u0086\7u\2\2\u0086"+
		"\u0087\7v\2\2\u0087\u0088\7\60\2\2\u0088\u0089\7o\2\2\u0089\u008a\7T\2"+
		"\2\u008a\u008b\7g\2\2\u008b\u008c\7o\2\2\u008c\u008d\7q\2\2\u008d\u008e"+
		"\7x\2\2\u008e\u008f\7g\2\2\u008f\24\3\2\2\2\u0090\u0091\7u\2\2\u0091\u0092"+
		"\7g\2\2\u0092\u0093\7v\2\2\u0093\u0094\7\60\2\2\u0094\u0095\7r\2\2\u0095"+
		"\u0096\7w\2\2\u0096\u0097\7v\2\2\u0097\26\3\2\2\2\u0098\u0099\7u\2\2\u0099"+
		"\u009a\7g\2\2\u009a\u009b\7v\2\2\u009b\u009c\7\60\2\2\u009c\u009d\7i\2"+
		"\2\u009d\u009e\7g\2\2\u009e\u009f\7v\2\2\u009f\30\3\2\2\2\u00a0\u00a1"+
		"\7u\2\2\u00a1\u00a2\7g\2\2\u00a2\u00a3\7v\2\2\u00a3\u00a4\7\60\2\2\u00a4"+
		"\u00a5\7r\2\2\u00a5\u00a6\7w\2\2\u00a6\u00a7\7v\2\2\u00a7\u00a8\7K\2\2"+
		"\u00a8\u00a9\7v\2\2\u00a9\u00aa\7g\2\2\u00aa\u00ab\7o\2\2\u00ab\32\3\2"+
		"\2\2\u00ac\u00ad\7u\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af\7v\2\2\u00af\u00b0"+
		"\7\60\2\2\u00b0\u00b1\7t\2\2\u00b1\u00b2\7g\2\2\u00b2\u00b3\7o\2\2\u00b3"+
		"\u00b4\7q\2\2\u00b4\u00b5\7x\2\2\u00b5\u00b6\7g\2\2\u00b6\34\3\2\2\2\u00b7"+
		"\u00b8\7u\2\2\u00b8\u00b9\7g\2\2\u00b9\u00ba\7v\2\2\u00ba\u00bb\7\60\2"+
		"\2\u00bb\u00bc\7t\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7o\2\2\u00be\u00bf"+
		"\7q\2\2\u00bf\u00c0\7x\2\2\u00c0\u00c1\7g\2\2\u00c1\u00c2\7K\2\2\u00c2"+
		"\u00c3\7v\2\2\u00c3\u00c4\7g\2\2\u00c4\u00c5\7o\2\2\u00c5\36\3\2\2\2\u00c6"+
		"\u00c7\7u\2\2\u00c7\u00c8\7g\2\2\u00c8\u00c9\7v\2\2\u00c9\u00ca\7\60\2"+
		"\2\u00ca\u00cb\7g\2\2\u00cb\u00cc\7z\2\2\u00cc\u00cd\7k\2\2\u00cd\u00ce"+
		"\7u\2\2\u00ce\u00cf\7v\2\2\u00cf\u00d0\7u\2\2\u00d0 \3\2\2\2\u00d1\u00d2"+
		"\7u\2\2\u00d2\u00d3\7g\2\2\u00d3\u00d4\7v\2\2\u00d4\u00d5\7\60\2\2\u00d5"+
		"\u00d6\7f\2\2\u00d6\u00d7\7t\2\2\u00d7\u00d8\7q\2\2\u00d8\u00d9\7r\2\2"+
		"\u00d9\"\3\2\2\2\u00da\u00db\7f\2\2\u00db\u00dc\7k\2\2\u00dc\u00dd\7e"+
		"\2\2\u00dd\u00de\7v\2\2\u00de\u00df\7\60\2\2\u00df\u00e0\7r\2\2\u00e0"+
		"\u00e1\7w\2\2\u00e1\u00e2\7v\2\2\u00e2$\3\2\2\2\u00e3\u00e4\7f\2\2\u00e4"+
		"\u00e5\7k\2\2\u00e5\u00e6\7e\2\2\u00e6\u00e7\7v\2\2\u00e7\u00e8\7\60\2"+
		"\2\u00e8\u00e9\7i\2\2\u00e9\u00ea\7g\2\2\u00ea\u00eb\7v\2\2\u00eb&\3\2"+
		"\2\2\u00ec\u00ed\7f\2\2\u00ed\u00ee\7k\2\2\u00ee\u00ef\7e\2\2\u00ef\u00f0"+
		"\7v\2\2\u00f0\u00f1\7\60\2\2\u00f1\u00f2\7r\2\2\u00f2\u00f3\7w\2\2\u00f3"+
		"\u00f4\7v\2\2\u00f4\u00f5\7K\2\2\u00f5\u00f6\7v\2\2\u00f6\u00f7\7g\2\2"+
		"\u00f7\u00f8\7o\2\2\u00f8(\3\2\2\2\u00f9\u00fa\7f\2\2\u00fa\u00fb\7k\2"+
		"\2\u00fb\u00fc\7e\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe\7\60\2\2\u00fe\u00ff"+
		"\7i\2\2\u00ff\u0100\7g\2\2\u0100\u0101\7v\2\2\u0101\u0102\7K\2\2\u0102"+
		"\u0103\7v\2\2\u0103\u0104\7g\2\2\u0104\u0105\7o\2\2\u0105*\3\2\2\2\u0106"+
		"\u0107\7f\2\2\u0107\u0108\7k\2\2\u0108\u0109\7e\2\2\u0109\u010a\7v\2\2"+
		"\u010a\u010b\7\60\2\2\u010b\u010c\7r\2\2\u010c\u010d\7q\2\2\u010d\u010e"+
		"\7r\2\2\u010e\u010f\7K\2\2\u010f\u0110\7v\2\2\u0110\u0111\7g\2\2\u0111"+
		"\u0112\7o\2\2\u0112,\3\2\2\2\u0113\u0114\7f\2\2\u0114\u0115\7k\2\2\u0115"+
		"\u0116\7e\2\2\u0116\u0117\7v\2\2\u0117\u0118\7\60\2\2\u0118\u0119\7t\2"+
		"\2\u0119\u011a\7g\2\2\u011a\u011b\7o\2\2\u011b\u011c\7q\2\2\u011c\u011d"+
		"\7x\2\2\u011d\u011e\7g\2\2\u011e\u011f\7K\2\2\u011f\u0120\7v\2\2\u0120"+
		"\u0121\7g\2\2\u0121\u0122\7o\2\2\u0122.\3\2\2\2\u0123\u0124\7f\2\2\u0124"+
		"\u0125\7k\2\2\u0125\u0126\7e\2\2\u0126\u0127\7v\2\2\u0127\u0128\7\60\2"+
		"\2\u0128\u0129\7f\2\2\u0129\u012a\7t\2\2\u012a\u012b\7q\2\2\u012b\u012c"+
		"\7r\2\2\u012c\60\3\2\2\2\u012d\u0131\t\2\2\2\u012e\u0130\t\3\2\2\u012f"+
		"\u012e\3\2\2\2\u0130\u0133\3\2\2\2\u0131\u012f\3\2\2\2\u0131\u0132\3\2"+
		"\2\2\u0132\u0136\3\2\2\2\u0133\u0131\3\2\2\2\u0134\u0136\7\62\2\2\u0135"+
		"\u012d\3\2\2\2\u0135\u0134\3\2\2\2\u0136\62\3\2\2\2\u0137\u0139\n\4\2"+
		"\2\u0138\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b"+
		"\3\2\2\2\u013b\64\3\2\2\2\u013c\u013e\t\4\2\2\u013d\u013c\3\2\2\2\u013e"+
		"\u013f\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0141\3\2"+
		"\2\2\u0141\u0142\b\33\2\2\u0142\66\3\2\2\2\7\2\u0131\u0135\u013a\u013f"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}