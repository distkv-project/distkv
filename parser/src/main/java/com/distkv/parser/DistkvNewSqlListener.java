package com.distkv.parser;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.ACTIVE_NAMESPACE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DEACTIVE_NAMESPACE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_GET;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_GET_ITEM;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_POP_ITEM;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_PUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_PUT_ITEM;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DICT_REMOVE_ITEM;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DROP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXISTS;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXIT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.EXPIRE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.INT_GET;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.INT_INCR;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.INT_PUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_GET;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_LPUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_MREMOVE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_PUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_REMOVE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.LIST_RPUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SET_EXISTS;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SET_GET;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SET_PUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SET_PUT_ITEM;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SET_REMOVE_ITEM;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SLIST_GET_MEMBER;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SLIST_INCR_SCORE;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SLIST_PUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SLIST_PUT_MEMBER;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SLIST_REMOVE_MEMBER;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.SLIST_TOP;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.STR_GET;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.STR_PUT;
import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.TTL;

import com.distkv.parser.generated.DistkvNewSQLBaseListener;
import com.distkv.parser.generated.DistkvNewSQLParser.TtlContext;
import com.distkv.parser.generated.DistkvNewSQLParser.ExistsContext;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.google.common.base.Preconditions;
import com.google.protobuf.Any;
import org.antlr.v4.runtime.tree.ParseTree;
import com.distkv.parser.generated.DistkvNewSQLParser;
import com.distkv.parser.po.DistkvParsedResult;

import java.util.ArrayList;
import java.util.List;


public class DistkvNewSqlListener extends DistkvNewSQLBaseListener {

  private DistkvParsedResult parsedResult = null;

  public DistkvParsedResult getParsedResult() {
    return parsedResult;
  }

  @Override
  public void enterExit(DistkvNewSQLParser.ExitContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);
    parsedResult = new DistkvParsedResult(EXIT, null);
  }

  @Override
  public void enterActiveNamespace(DistkvNewSQLParser.ActiveNamespaceContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setNamespace(ctx.children.get(1).getText())
        .build();
    parsedResult = new DistkvParsedResult(ACTIVE_NAMESPACE, request);
  }

  @Override
  public void enterDeactiveNamespace(DistkvNewSQLParser.DeactiveNamespaceContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);
    parsedResult = new DistkvParsedResult(DEACTIVE_NAMESPACE, null);
  }

  @Override
  public void enterStrPut(DistkvNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.StrPutRequest.Builder builder = StringProtocol.StrPutRequest.newBuilder();
    builder.setValue(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(STR_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(STR_PUT, request);
  }

  @Override
  public void enterStrGet(DistkvNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(STR_GET)
        .build();
    parsedResult = new DistkvParsedResult(STR_GET, request);
  }

  @Override
  public void enterListPut(DistkvNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListPutRequest.Builder builder = ListProtocol.ListPutRequest.newBuilder();
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(LIST_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_PUT, request);
  }

  @Override
  public void enterListLput(DistkvNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.ListLPutRequest.Builder builder = ListProtocol.ListLPutRequest.newBuilder();
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(LIST_LPUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_LPUT, request);
  }

  @Override
  public void enterListRput(DistkvNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.ListRPutRequest.Builder builder = ListProtocol.ListRPutRequest.newBuilder();
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(LIST_RPUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_RPUT, request);
  }

  @Override
  public void enterListGetAll(DistkvNewSQLParser.ListGetAllContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);

    ListProtocol.ListGetRequest.Builder builder =
        ListProtocol.ListGetRequest.newBuilder();
    builder.setType(ListProtocol.GetType.GET_ALL);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(LIST_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_GET, request);
  }

  @Override
  public void enterListGetOne(DistkvNewSQLParser.ListGetOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.ListGetRequest.Builder builder = ListProtocol.ListGetRequest
        .newBuilder();
    builder.setIndex(Integer.parseInt(ctx.getChild(1).getText()));
    builder.setType(ListProtocol.GetType.GET_ONE);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(LIST_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_GET, request);
  }

  @Override
  public void enterListGetRange(DistkvNewSQLParser.ListGetRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListGetRequest.Builder builder = ListProtocol.ListGetRequest
        .newBuilder();
    builder.setFrom(Integer.parseInt(ctx.getChild(1).getText()));
    builder.setEnd(Integer.parseInt(ctx.getChild(2).getText()));
    builder.setType(ListProtocol.GetType.GET_RANGE);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(LIST_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_GET, request);
  }

  @Override
  public void enterListRemoveOne(DistkvNewSQLParser.ListRemoveOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    ListProtocol.ListRemoveRequest.Builder removeRequestBuilder =
        ListProtocol.ListRemoveRequest.newBuilder();
    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
    removeRequestBuilder.setIndex(Integer.parseInt(ctx.children.get(1).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(LIST_REMOVE)
        .setRequest(Any.pack(removeRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        LIST_REMOVE, request);
  }

  @Override
  public void enterListRemoveRange(DistkvNewSQLParser.ListRemoveRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListRemoveRequest.Builder removeRequestBuilder =
        ListProtocol.ListRemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
    removeRequestBuilder.setFrom(Integer.parseInt(ctx.children.get(1).getText()));
    removeRequestBuilder.setEnd(Integer.parseInt(ctx.children.get(2).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(LIST_REMOVE)
        .setRequest(Any.pack(removeRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        LIST_REMOVE, request);
  }

  @Override
  public void enterListMRemove(DistkvNewSQLParser.ListMRemoveContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() >= 3);

    ListProtocol.ListMRemoveRequest.Builder mremoveRequest = ListProtocol.ListMRemoveRequest
        .newBuilder();
    List<Integer> indexesList = new ArrayList<>();
    for (int i = 2; i < ctx.children.size(); i++) {
      indexesList.add(Integer.valueOf(ctx.children.get(i).getText()));
    }
    mremoveRequest.addAllIndexes(indexesList);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(LIST_MREMOVE)
        .setRequest(Any.pack(mremoveRequest.build()))
        .build();
    parsedResult = new DistkvParsedResult(LIST_MREMOVE, request);
  }

  @Override
  public void enterSetPut(DistkvNewSQLParser.SetPutContext ctx) {
    // The children of the `set_put` should be 3:
    //        `set.put    key     value_array`
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SetProtocol.SetPutRequest.Builder builder = SetProtocol.SetPutRequest.newBuilder();
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SET_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SET_PUT, request);
  }

  @Override
  public void enterSetGet(DistkvNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SET_GET)
        .build();
    parsedResult = new DistkvParsedResult(SET_GET, request);
  }

  @Override
  public void enterSetPutItem(DistkvNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetPutItemRequest.Builder builder = SetProtocol.SetPutItemRequest.newBuilder();
    builder.setItemValue(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SET_PUT_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SET_PUT_ITEM, request);
  }

  @Override
  public void enterSetRemoveItem(DistkvNewSQLParser.SetRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.getChildCount() == 3);
    SetProtocol.SetRemoveItemRequest.Builder builder = SetProtocol.SetRemoveItemRequest
        .newBuilder();
    builder.setItemValue(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SET_REMOVE_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SET_REMOVE_ITEM, request);
  }

  @Override
  public void enterSetExists(DistkvNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetExistsRequest.Builder builder = SetProtocol.SetExistsRequest.newBuilder();
    builder.setEntity(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SET_EXISTS)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SET_EXISTS, request);
  }

  @Override
  public void enterDictPut(DistkvNewSQLParser.DictPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictPutRequest.Builder builder = DictProtocol.DictPutRequest.newBuilder();
    final ParseTree keyValuePairsParseTree = ctx.children.get(2);
    final int numKeyValuePairs = keyValuePairsParseTree.getChildCount();
    DictProtocol.DistKVDict.Builder distKVDictBuilder = DictProtocol.DistKVDict.newBuilder();
    for (int i = 0; i < numKeyValuePairs; ++i) {
      final ParseTree keyValuePairParseTree = keyValuePairsParseTree.getChild(i);
      Preconditions.checkState(keyValuePairParseTree.getChildCount() == 2);
      distKVDictBuilder.addKeys(keyValuePairParseTree.getChild(0).getText());
      distKVDictBuilder.addValues(keyValuePairParseTree.getChild(1).getText());
    }
    builder.setDict(distKVDictBuilder.build());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DICT_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(DICT_PUT, request);
  }

  @Override
  public void enterDictGet(DistkvNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.DictGetRequest.Builder builder = DictProtocol.DictGetRequest.newBuilder();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DICT_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(DICT_GET, request);
  }

  @Override
  public void enterDictPutItem(DistkvNewSQLParser.DictPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);
    DictProtocol.DictPutItemRequest.Builder builder = DictProtocol.DictPutItemRequest.newBuilder();
    builder.setItemKey(ctx.children.get(2).getText());
    builder.setItemValue(ctx.children.get(3).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DICT_PUT_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(DICT_PUT_ITEM, request);
  }

  @Override
  public void enterDictGetItem(DistkvNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictGetItemRequest.Builder builder = DictProtocol.DictGetItemRequest.newBuilder();
    builder.setItemKey(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DICT_GET_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(DICT_GET_ITEM, request);
  }

  @Override
  public void enterDictPopItem(DistkvNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictPopItemRequest.Builder builder = DictProtocol.DictPopItemRequest.newBuilder();
    builder.setItemKey(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DICT_POP_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(DICT_POP_ITEM, request);
  }

  @Override
  public void enterDictRemoveItem(DistkvNewSQLParser.DictRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictRemoveItemRequest.Builder builder = DictProtocol.DictRemoveItemRequest
        .newBuilder();
    builder.setItemKey(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DICT_REMOVE_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(DICT_REMOVE_ITEM, request);
  }

  @Override
  public void enterSlistPut(DistkvNewSQLParser.SlistPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SlistProtocol.SlistPutRequest.Builder slistPutRequestBuilder =
        SlistProtocol.SlistPutRequest.newBuilder();
    final ParseTree sortedListEntityPairsParseTree = ctx.children.get(2);
    final int sortedListEntityPairs = sortedListEntityPairsParseTree.getChildCount();
    for (int i = 0; i < sortedListEntityPairs; i++) {
      final SlistProtocol.SlistEntity.Builder slistBuilder =
          SlistProtocol.SlistEntity.newBuilder();
      final ParseTree sortedListEntityParseTree =
          sortedListEntityPairsParseTree.getChild(i);
      Preconditions.checkState(sortedListEntityParseTree.getChildCount() == 2);
      slistBuilder.setScore(Integer.parseInt(sortedListEntityParseTree.getChild(1).getText()));
      slistBuilder.setMember(sortedListEntityParseTree.getChild(0).getText());
      slistPutRequestBuilder.addList(slistBuilder);
    }
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SLIST_PUT)
        .setRequest(Any.pack(slistPutRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        SLIST_PUT, request);
  }

  @Override
  public void enterSlistTop(DistkvNewSQLParser.SlistTopContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SlistProtocol.SlistTopRequest.Builder slistTopRequestBuilder =
        SlistProtocol.SlistTopRequest.newBuilder();
    slistTopRequestBuilder.setCount(Integer.parseInt(ctx.children.get(2).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SLIST_TOP)
        .setRequest(Any.pack(slistTopRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        SLIST_TOP, request);
  }

  @Override
  public void enterSlistIncrScoreDefault(DistkvNewSQLParser.SlistIncrScoreDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    SlistProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SlistProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(1);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(SLIST_INCR_SCORE)
        .setRequest(Any.pack(slistIncrScoreRequest.build()))
        .build();
    parsedResult = new DistkvParsedResult(SLIST_INCR_SCORE,
        request);
  }

  @Override
  public void enterSlistIncrScoreDelta(DistkvNewSQLParser.SlistIncrScoreDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SlistProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SlistProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(Integer.parseInt(ctx.children.get(2).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(SLIST_INCR_SCORE)
        .setRequest(Any.pack(slistIncrScoreRequest.build()))
        .build();
    parsedResult = new DistkvParsedResult(SLIST_INCR_SCORE,
        request);
  }

  @Override
  public void enterSlistPutMember(DistkvNewSQLParser.SlistPutMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);

    SlistProtocol.SlistPutMemberRequest.Builder slistPutMemberRequestBuilder =
        SlistProtocol.SlistPutMemberRequest.newBuilder();
    slistPutMemberRequestBuilder.setScore(Integer.parseInt(ctx.children.get(3).getText()));
    slistPutMemberRequestBuilder.setMember(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SLIST_PUT_MEMBER)
        .setRequest(Any.pack(slistPutMemberRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SLIST_PUT_MEMBER,
        request);
  }

  @Override
  public void enterSlistRemoveMember(DistkvNewSQLParser.SlistRemoveMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SlistProtocol.SlistRemoveMemberRequest.Builder removeMemberRequestBuilder =
        SlistProtocol.SlistRemoveMemberRequest.newBuilder();
    removeMemberRequestBuilder.setMember(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SLIST_REMOVE_MEMBER)
        .setRequest(Any.pack(removeMemberRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SLIST_REMOVE_MEMBER,
        request);
  }

  @Override
  public void enterSlistGetMember(DistkvNewSQLParser.SlistGetMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SlistProtocol.SlistGetMemberRequest.Builder getMemberRequestBuilder =
        SlistProtocol.SlistGetMemberRequest.newBuilder();
    getMemberRequestBuilder.setMember(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(SLIST_GET_MEMBER)
        .setRequest(Any.pack(getMemberRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(SLIST_GET_MEMBER,
        request);
  }

  @Override
  public void enterIntPut(DistkvNewSQLParser.IntPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    IntProtocol.IntPutRequest.Builder intPutBuilder =
            IntProtocol.IntPutRequest.newBuilder();
    intPutBuilder.setValue(Integer.parseInt(ctx.children.get(2).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
          .setKey(ctx.children.get(1).getText())
          .setRequestType(INT_PUT)
          .setRequest(Any.pack(intPutBuilder.build()))
          .build();
    parsedResult = new DistkvParsedResult(INT_PUT, request);
  }

  @Override
  public void enterIntGet(DistkvNewSQLParser.IntGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
            .setKey(ctx.children.get(1).getText())
            .setRequestType(INT_GET)
            .build();
    parsedResult = new DistkvParsedResult(INT_GET, request);
  }

  @Override
  public void enterIntIncrDefault(DistkvNewSQLParser.IntIncrDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);

    IntProtocol.IntIncrRequest.Builder intIncrBuilder =
            IntProtocol.IntIncrRequest.newBuilder();
    intIncrBuilder.setDelta(1);
    DistkvRequest request = DistkvRequest.newBuilder()
            .setKey(ctx.children.get(0).getText())
            .setRequestType(INT_INCR)
            .setRequest(Any.pack(intIncrBuilder.build()))
            .build();
    parsedResult = new DistkvParsedResult(INT_INCR, request);
  }

  @Override
  public void enterIntIncrDelta(DistkvNewSQLParser.IntIncrDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    IntProtocol.IntIncrRequest.Builder intIncrBuilder =
            IntProtocol.IntIncrRequest.newBuilder();
    intIncrBuilder.setDelta(Integer.parseInt(ctx.children.get(1).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
            .setKey(ctx.children.get(0).getText())
            .setRequestType(INT_INCR)
            .setRequest(Any.pack(intIncrBuilder.build()))
            .build();
    parsedResult = new DistkvParsedResult(INT_INCR, request);
  }

  @Override
  public void enterExpire(DistkvNewSQLParser.ExpireContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ExpireRequest expireRequest = ExpireRequest
        .newBuilder()
        .setExpireTime(Integer.parseInt(ctx.children.get(2).getText()))
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(EXPIRE)
        .setRequest(Any.pack(expireRequest))
        .build();
    parsedResult = new DistkvParsedResult(EXPIRE, request);
  }

  @Override
  public void enterTtl(TtlContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(TTL)
        .build();
    parsedResult = new DistkvParsedResult(TTL, request);
  }

  @Override
  public void enterDrop(DistkvNewSQLParser.DropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(DROP)
        .build();
    parsedResult = new DistkvParsedResult(DROP, request);
  }

  @Override
  public void enterExists(ExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(EXISTS)
        .build();
    parsedResult = new DistkvParsedResult(EXISTS, request);
  }

}
