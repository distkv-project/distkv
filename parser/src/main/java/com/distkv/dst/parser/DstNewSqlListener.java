package com.distkv.dst.parser;

import com.distkv.dst.parser.po.RequestTypeEnum;
import com.google.common.base.Preconditions;
import org.antlr.v4.runtime.tree.ParseTree;
import com.distkv.dst.parser.generated.DstNewSQLBaseListener;
import com.distkv.dst.parser.generated.DstNewSQLParser;
import com.distkv.dst.parser.po.DstParsedResult;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DstNewSqlListener extends DstNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstNewSqlListener.class);

  private DstParsedResult parsedResult = null;

  public DstParsedResult getParsedResult() {
    return parsedResult;
  }

  @Override
  public void enterStrPut(DstNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.PutRequest.Builder builder = StringProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_PUT, builder.build());
  }

  @Override
  public void enterStrGet(DstNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    StringProtocol.GetRequest.Builder builder = StringProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.STR_GET, builder.build());
  }

  @Override
  public void enterListPut(DstNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.PutRequest.Builder builder = ListProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_PUT, builder.build());
  }

  @Override
  public void enterListLput(DstNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.LPutRequest.Builder builder = ListProtocol.LPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_LPUT, builder.build());
  }

  @Override
  public void enterListRput(DstNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.RPutRequest.Builder builder = ListProtocol.RPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_RPUT, builder.build());
  }

  @Override
  public void enterListGet(DstNewSQLParser.ListGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    final ParseTree listGetArgumentsParseTree = ctx.children.get(1);
    final int numArguments = listGetArgumentsParseTree.getChildCount();

    ListProtocol.GetRequest.Builder builder = ListProtocol.GetRequest.newBuilder();
    final String key = listGetArgumentsParseTree.getChild(0).getText();
    builder.setKey(key);
    if (1 == numArguments) {
      // GET_ALL
      Preconditions.checkState(listGetArgumentsParseTree.getChildCount() == 1);
      builder.setType(ListProtocol.GetType.GET_ALL);
    } else if (2 == numArguments) {
      // GET_ONE
      Preconditions.checkState(listGetArgumentsParseTree.getChildCount() == 2);
      builder.setType(ListProtocol.GetType.GET_ONE);
      builder.setIndex(Integer.valueOf(listGetArgumentsParseTree.getChild(1).getText()));
    } else if (3 == numArguments) {
      // GET_RANGE
      Preconditions.checkState(listGetArgumentsParseTree.getChildCount() == 3);
      builder.setType(ListProtocol.GetType.GET_RANGE);
      builder.setFrom(Integer.valueOf(listGetArgumentsParseTree.getChild(1).getText()));
      builder.setEnd(Integer.valueOf(listGetArgumentsParseTree.getChild(2).getText()));
    } else {
      throw new RuntimeException("Failed to parser the command.");
    }

    parsedResult = new DstParsedResult(RequestTypeEnum.LIST_GET, builder.build());
  }

  @Override
  public void enterListRGet(DstNewSQLParser.ListRGetContext ctx) {
    // TODO(qwang): Refine.
  }

  @Override
  public void enterListDelete(DstNewSQLParser.ListDeleteContext ctx) {
    // TODO(qwang): Refine.
  }

  @Override
  public void enterListMDelete(DstNewSQLParser.ListMDeleteContext ctx) {
    // TODO(qwang): Refine.
  }

  @Override
  public void enterSetPut(DstNewSQLParser.SetPutContext ctx) {
    // The children of the `set_put` should be 3:
    //        `set.put    key     value_array`
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SetProtocol.PutRequest.Builder builder = SetProtocol.PutRequest.newBuilder();
    final String key = ctx.children.get(1).getText();
    builder.setKey(key);
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    SetProtocol.PutRequest request = builder.build();
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DstNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.GetRequest.Builder builder = SetProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_GET, builder.build());
  }

  @Override
  public void enterSetDrop(DstNewSQLParser.SetDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_DROP, builder.build());
  }

  @Override
  public void enterSetPutItem(DstNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.PutItemRequest.Builder builder = SetProtocol.PutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_PUT_ITEM, builder.build());
  }

  @Override
  public void enterSetRemoveItem(DstNewSQLParser.SetRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.getChildCount() == 3);
    SetProtocol.RemoveItemRequest.Builder builder = SetProtocol.RemoveItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_REMOVE_ITEM, builder);
  }

  @Override
  public void enterSetExists(DstNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.ExistsRequest.Builder builder = SetProtocol.ExistsRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setEntity(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.SET_EXIST, builder);
  }

  @Override
  public void enterDictPut(DstNewSQLParser.DictPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.PutRequest.Builder builder = DictProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final ParseTree keyValuePairsParseTree = ctx.children.get(2);
    final int numKeyValuePairs = keyValuePairsParseTree.getChildCount();
    DictProtocol.DstDict.Builder dstDictBuilder = DictProtocol.DstDict.newBuilder();
    for (int i = 0; i < numKeyValuePairs; ++i) {
      final ParseTree keyValuePairParseTree = keyValuePairsParseTree.getChild(i);
      Preconditions.checkState(keyValuePairParseTree.getChildCount() == 2);
      dstDictBuilder.addKeys(keyValuePairParseTree.getChild(0).getText());
      dstDictBuilder.addValues(keyValuePairParseTree.getChild(1).getText());
    }
    builder.setDict(dstDictBuilder.build());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_PUT, builder.build());
  }

  @Override
  public void enterDictGet(DstNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.GetRequest.Builder builder = DictProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_GET, builder.build());
  }

  @Override
  public void enterDictPutItem(DstNewSQLParser.DictPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);
    DictProtocol.PutItemRequest.Builder builder = DictProtocol.PutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    builder.setItemValue(ctx.children.get(3).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_PUT_ITEM, builder.build());
  }

  @Override
  public void enterDictGetItem(DstNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.GetItemRequest.Builder builder
        = DictProtocol.GetItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_GET_ITEM, builder.build());
  }

  @Override
  public void enterDictPopItem(DstNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.PopItemRequest.Builder builder = DictProtocol.PopItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_POP_ITEM, builder.build());
  }

  @Override
  public void enterDictRemoveItem(DstNewSQLParser.DictRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.RemoveItemRequest.Builder builder = DictProtocol.RemoveItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterDictDrop(DstNewSQLParser.DictDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DstParsedResult(RequestTypeEnum.DICT_DROP, builder.build());
  }

}
