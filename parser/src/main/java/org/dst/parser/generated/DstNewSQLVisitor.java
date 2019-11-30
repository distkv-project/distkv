// Generated from DstNewSQL.g4 by ANTLR 4.7

package org.dst.parser.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DstNewSQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DstNewSQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(DstNewSQLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#conceptStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConceptStatement(DstNewSQLParser.ConceptStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#strStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrStatement(DstNewSQLParser.StrStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#strPut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrPut(DstNewSQLParser.StrPutContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#strGet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrGet(DstNewSQLParser.StrGetContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListStatement(DstNewSQLParser.ListStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listPut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListPut(DstNewSQLParser.ListPutContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listLput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLput(DstNewSQLParser.ListLputContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listRput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListRput(DstNewSQLParser.ListRputContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listGet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListGet(DstNewSQLParser.ListGetContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listRGet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListRGet(DstNewSQLParser.ListRGetContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listDelete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListDelete(DstNewSQLParser.ListDeleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listMDelete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListMDelete(DstNewSQLParser.ListMDeleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listGetArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListGetArguments(DstNewSQLParser.ListGetArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listGetAll}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListGetAll(DstNewSQLParser.ListGetAllContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listGetOne}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListGetOne(DstNewSQLParser.ListGetOneContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listGetRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListGetRange(DstNewSQLParser.ListGetRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listRemoveOne}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListRemoveOne(DstNewSQLParser.ListRemoveOneContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#listRemoveRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListRemoveRange(DstNewSQLParser.ListRemoveRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStatement(DstNewSQLParser.SetStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setPut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetPut(DstNewSQLParser.SetPutContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setGet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetGet(DstNewSQLParser.SetGetContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setPutItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetPutItem(DstNewSQLParser.SetPutItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setRemoveItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetRemoveItem(DstNewSQLParser.SetRemoveItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setExists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetExists(DstNewSQLParser.SetExistsContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#setDrop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetDrop(DstNewSQLParser.SetDropContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictStatement(DstNewSQLParser.DictStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictPut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictPut(DstNewSQLParser.DictPutContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictGet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictGet(DstNewSQLParser.DictGetContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictPutItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictPutItem(DstNewSQLParser.DictPutItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictGetItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictGetItem(DstNewSQLParser.DictGetItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictPopItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictPopItem(DstNewSQLParser.DictPopItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictRemoveItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictRemoveItem(DstNewSQLParser.DictRemoveItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#dictDrop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictDrop(DstNewSQLParser.DictDropContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#keyValuePairs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyValuePairs(DstNewSQLParser.KeyValuePairsContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#keyValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyValuePair(DstNewSQLParser.KeyValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#itemKey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItemKey(DstNewSQLParser.ItemKeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#itemValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItemValue(DstNewSQLParser.ItemValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#key}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKey(DstNewSQLParser.KeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(DstNewSQLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#valueArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArray(DstNewSQLParser.ValueArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link DstNewSQLParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(DstNewSQLParser.IndexContext ctx);
}