// Generated from DstNewSQL.g4 by ANTLR 4.7

package org.dst.parser.generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DstNewSQLParser}.
 */
public interface DstNewSQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DstNewSQLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DstNewSQLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#conceptStatement}.
	 * @param ctx the parse tree
	 */
	void enterConceptStatement(DstNewSQLParser.ConceptStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#conceptStatement}.
	 * @param ctx the parse tree
	 */
	void exitConceptStatement(DstNewSQLParser.ConceptStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#strStatement}.
	 * @param ctx the parse tree
	 */
	void enterStrStatement(DstNewSQLParser.StrStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#strStatement}.
	 * @param ctx the parse tree
	 */
	void exitStrStatement(DstNewSQLParser.StrStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#strPut}.
	 * @param ctx the parse tree
	 */
	void enterStrPut(DstNewSQLParser.StrPutContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#strPut}.
	 * @param ctx the parse tree
	 */
	void exitStrPut(DstNewSQLParser.StrPutContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#strGet}.
	 * @param ctx the parse tree
	 */
	void enterStrGet(DstNewSQLParser.StrGetContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#strGet}.
	 * @param ctx the parse tree
	 */
	void exitStrGet(DstNewSQLParser.StrGetContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listStatement}.
	 * @param ctx the parse tree
	 */
	void enterListStatement(DstNewSQLParser.ListStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listStatement}.
	 * @param ctx the parse tree
	 */
	void exitListStatement(DstNewSQLParser.ListStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listPut}.
	 * @param ctx the parse tree
	 */
	void enterListPut(DstNewSQLParser.ListPutContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listPut}.
	 * @param ctx the parse tree
	 */
	void exitListPut(DstNewSQLParser.ListPutContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listLput}.
	 * @param ctx the parse tree
	 */
	void enterListLput(DstNewSQLParser.ListLputContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listLput}.
	 * @param ctx the parse tree
	 */
	void exitListLput(DstNewSQLParser.ListLputContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listRput}.
	 * @param ctx the parse tree
	 */
	void enterListRput(DstNewSQLParser.ListRputContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listRput}.
	 * @param ctx the parse tree
	 */
	void exitListRput(DstNewSQLParser.ListRputContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listGet}.
	 * @param ctx the parse tree
	 */
	void enterListGet(DstNewSQLParser.ListGetContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listGet}.
	 * @param ctx the parse tree
	 */
	void exitListGet(DstNewSQLParser.ListGetContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listRGet}.
	 * @param ctx the parse tree
	 */
	void enterListRGet(DstNewSQLParser.ListRGetContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listRGet}.
	 * @param ctx the parse tree
	 */
	void exitListRGet(DstNewSQLParser.ListRGetContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listDelete}.
	 * @param ctx the parse tree
	 */
	void enterListDelete(DstNewSQLParser.ListDeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listDelete}.
	 * @param ctx the parse tree
	 */
	void exitListDelete(DstNewSQLParser.ListDeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listMDelete}.
	 * @param ctx the parse tree
	 */
	void enterListMDelete(DstNewSQLParser.ListMDeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listMDelete}.
	 * @param ctx the parse tree
	 */
	void exitListMDelete(DstNewSQLParser.ListMDeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listGetArguments}.
	 * @param ctx the parse tree
	 */
	void enterListGetArguments(DstNewSQLParser.ListGetArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listGetArguments}.
	 * @param ctx the parse tree
	 */
	void exitListGetArguments(DstNewSQLParser.ListGetArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listGetAll}.
	 * @param ctx the parse tree
	 */
	void enterListGetAll(DstNewSQLParser.ListGetAllContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listGetAll}.
	 * @param ctx the parse tree
	 */
	void exitListGetAll(DstNewSQLParser.ListGetAllContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listGetOne}.
	 * @param ctx the parse tree
	 */
	void enterListGetOne(DstNewSQLParser.ListGetOneContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listGetOne}.
	 * @param ctx the parse tree
	 */
	void exitListGetOne(DstNewSQLParser.ListGetOneContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listGetRange}.
	 * @param ctx the parse tree
	 */
	void enterListGetRange(DstNewSQLParser.ListGetRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listGetRange}.
	 * @param ctx the parse tree
	 */
	void exitListGetRange(DstNewSQLParser.ListGetRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listRemoveOne}.
	 * @param ctx the parse tree
	 */
	void enterListRemoveOne(DstNewSQLParser.ListRemoveOneContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listRemoveOne}.
	 * @param ctx the parse tree
	 */
	void exitListRemoveOne(DstNewSQLParser.ListRemoveOneContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#listRemoveRange}.
	 * @param ctx the parse tree
	 */
	void enterListRemoveRange(DstNewSQLParser.ListRemoveRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#listRemoveRange}.
	 * @param ctx the parse tree
	 */
	void exitListRemoveRange(DstNewSQLParser.ListRemoveRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(DstNewSQLParser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(DstNewSQLParser.SetStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setPut}.
	 * @param ctx the parse tree
	 */
	void enterSetPut(DstNewSQLParser.SetPutContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setPut}.
	 * @param ctx the parse tree
	 */
	void exitSetPut(DstNewSQLParser.SetPutContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setGet}.
	 * @param ctx the parse tree
	 */
	void enterSetGet(DstNewSQLParser.SetGetContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setGet}.
	 * @param ctx the parse tree
	 */
	void exitSetGet(DstNewSQLParser.SetGetContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setPutItem}.
	 * @param ctx the parse tree
	 */
	void enterSetPutItem(DstNewSQLParser.SetPutItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setPutItem}.
	 * @param ctx the parse tree
	 */
	void exitSetPutItem(DstNewSQLParser.SetPutItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setRemoveItem}.
	 * @param ctx the parse tree
	 */
	void enterSetRemoveItem(DstNewSQLParser.SetRemoveItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setRemoveItem}.
	 * @param ctx the parse tree
	 */
	void exitSetRemoveItem(DstNewSQLParser.SetRemoveItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setExists}.
	 * @param ctx the parse tree
	 */
	void enterSetExists(DstNewSQLParser.SetExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setExists}.
	 * @param ctx the parse tree
	 */
	void exitSetExists(DstNewSQLParser.SetExistsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#setDrop}.
	 * @param ctx the parse tree
	 */
	void enterSetDrop(DstNewSQLParser.SetDropContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#setDrop}.
	 * @param ctx the parse tree
	 */
	void exitSetDrop(DstNewSQLParser.SetDropContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictStatement}.
	 * @param ctx the parse tree
	 */
	void enterDictStatement(DstNewSQLParser.DictStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictStatement}.
	 * @param ctx the parse tree
	 */
	void exitDictStatement(DstNewSQLParser.DictStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictPut}.
	 * @param ctx the parse tree
	 */
	void enterDictPut(DstNewSQLParser.DictPutContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictPut}.
	 * @param ctx the parse tree
	 */
	void exitDictPut(DstNewSQLParser.DictPutContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictGet}.
	 * @param ctx the parse tree
	 */
	void enterDictGet(DstNewSQLParser.DictGetContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictGet}.
	 * @param ctx the parse tree
	 */
	void exitDictGet(DstNewSQLParser.DictGetContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictPutItem}.
	 * @param ctx the parse tree
	 */
	void enterDictPutItem(DstNewSQLParser.DictPutItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictPutItem}.
	 * @param ctx the parse tree
	 */
	void exitDictPutItem(DstNewSQLParser.DictPutItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictGetItem}.
	 * @param ctx the parse tree
	 */
	void enterDictGetItem(DstNewSQLParser.DictGetItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictGetItem}.
	 * @param ctx the parse tree
	 */
	void exitDictGetItem(DstNewSQLParser.DictGetItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictPopItem}.
	 * @param ctx the parse tree
	 */
	void enterDictPopItem(DstNewSQLParser.DictPopItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictPopItem}.
	 * @param ctx the parse tree
	 */
	void exitDictPopItem(DstNewSQLParser.DictPopItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictRemoveItem}.
	 * @param ctx the parse tree
	 */
	void enterDictRemoveItem(DstNewSQLParser.DictRemoveItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictRemoveItem}.
	 * @param ctx the parse tree
	 */
	void exitDictRemoveItem(DstNewSQLParser.DictRemoveItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#dictDrop}.
	 * @param ctx the parse tree
	 */
	void enterDictDrop(DstNewSQLParser.DictDropContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#dictDrop}.
	 * @param ctx the parse tree
	 */
	void exitDictDrop(DstNewSQLParser.DictDropContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#keyValuePairs}.
	 * @param ctx the parse tree
	 */
	void enterKeyValuePairs(DstNewSQLParser.KeyValuePairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#keyValuePairs}.
	 * @param ctx the parse tree
	 */
	void exitKeyValuePairs(DstNewSQLParser.KeyValuePairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#keyValuePair}.
	 * @param ctx the parse tree
	 */
	void enterKeyValuePair(DstNewSQLParser.KeyValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#keyValuePair}.
	 * @param ctx the parse tree
	 */
	void exitKeyValuePair(DstNewSQLParser.KeyValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#itemKey}.
	 * @param ctx the parse tree
	 */
	void enterItemKey(DstNewSQLParser.ItemKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#itemKey}.
	 * @param ctx the parse tree
	 */
	void exitItemKey(DstNewSQLParser.ItemKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#itemValue}.
	 * @param ctx the parse tree
	 */
	void enterItemValue(DstNewSQLParser.ItemValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#itemValue}.
	 * @param ctx the parse tree
	 */
	void exitItemValue(DstNewSQLParser.ItemValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(DstNewSQLParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(DstNewSQLParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(DstNewSQLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(DstNewSQLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#valueArray}.
	 * @param ctx the parse tree
	 */
	void enterValueArray(DstNewSQLParser.ValueArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#valueArray}.
	 * @param ctx the parse tree
	 */
	void exitValueArray(DstNewSQLParser.ValueArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link DstNewSQLParser#index}.
	 * @param ctx the parse tree
	 */
	void enterIndex(DstNewSQLParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link DstNewSQLParser#index}.
	 * @param ctx the parse tree
	 */
	void exitIndex(DstNewSQLParser.IndexContext ctx);
}