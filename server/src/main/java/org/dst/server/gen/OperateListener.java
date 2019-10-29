// Generated from D:/dst/dst/server/src/main/java/org/dst/server/gen\Operate.g4 by ANTLR 4.7.2
package org.dst.server.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link OperateParser}.
 */
public interface OperateListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link OperateParser#opreate}.
	 * @param ctx the parse tree
	 */
	void enterOpreate(OperateParser.OpreateContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#opreate}.
	 * @param ctx the parse tree
	 */
	void exitOpreate(OperateParser.OpreateContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#set}.
	 * @param ctx the parse tree
	 */
	void enterSet(OperateParser.SetContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#set}.
	 * @param ctx the parse tree
	 */
	void exitSet(OperateParser.SetContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(OperateParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(OperateParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(OperateParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(OperateParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#dict}.
	 * @param ctx the parse tree
	 */
	void enterDict(OperateParser.DictContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#dict}.
	 * @param ctx the parse tree
	 */
	void exitDict(OperateParser.DictContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#setoperate}.
	 * @param ctx the parse tree
	 */
	void enterSetoperate(OperateParser.SetoperateContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#setoperate}.
	 * @param ctx the parse tree
	 */
	void exitSetoperate(OperateParser.SetoperateContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(OperateParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(OperateParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link OperateParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(OperateParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link OperateParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(OperateParser.ValueContext ctx);
}