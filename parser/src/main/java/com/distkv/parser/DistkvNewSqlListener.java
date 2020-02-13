package com.distkv.parser;

import com.distkv.parser.generated.DistkvNewSQLBaseListener;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
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
    parsedResult = new DistkvParsedResult(RequestType.EXIT, null);
  }

  @Override
  public void enterStrPut(DistkvNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.StrPutRequest.Builder builder = StringProtocol.StrPutRequest.newBuilder();
    builder.setValue(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.STR_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.STR_PUT, request);
  }

  @Override
  public void enterStrGet(DistkvNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.STR_GET)
        .build();
    parsedResult = new DistkvParsedResult(RequestType.STR_GET, request);
  }

  @Override
  public void enterStrDrop(DistkvNewSQLParser.StrDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.STR_DROP)
        .build();
    parsedResult = new DistkvParsedResult(RequestType.STR_DROP, request);
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
        .setRequestType(RequestType.LIST_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_PUT, request);
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
        .setRequestType(RequestType.LIST_LPUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_LPUT, request);
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
        .setRequestType(RequestType.LIST_RPUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_RPUT, request);
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
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_GET, request);
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
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_GET, request);
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
        .setRequestType(RequestType.LIST_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_GET, request);
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
        .setRequestType(RequestType.LIST_REMOVE)
        .setRequest(Any.pack(removeRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        RequestType.LIST_REMOVE, request);
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
        .setRequestType(RequestType.LIST_REMOVE)
        .setRequest(Any.pack(removeRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        RequestType.LIST_REMOVE, request);
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
        .setRequestType(RequestType.LIST_MREMOVE)
        .setRequest(Any.pack(mremoveRequest.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_MREMOVE, request);
  }

  @Override
  public void enterListDrop(DistkvNewSQLParser.ListDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.LIST_DROP)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.LIST_DROP, request);
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
        .setRequestType(RequestType.SET_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DistkvNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SET_GET)
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SET_GET, request);
  }

  @Override
  public void enterSetDrop(DistkvNewSQLParser.SetDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SET_DROP)
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SET_DROP, request);
  }

  @Override
  public void enterSetPutItem(DistkvNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetPutItemRequest.Builder builder = SetProtocol.SetPutItemRequest.newBuilder();
    builder.setItemValue(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SET_PUT_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SET_PUT_ITEM, request);
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
        .setRequestType(RequestType.SET_REMOVE_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SET_REMOVE_ITEM, request);
  }

  @Override
  public void enterSetExists(DistkvNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetExistsRequest.Builder builder = SetProtocol.SetExistsRequest.newBuilder();
    builder.setEntity(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SET_EXISTS)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SET_EXISTS, request);
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
        .setRequestType(RequestType.DICT_PUT)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_PUT, request);
  }

  @Override
  public void enterDictGet(DistkvNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.DictGetRequest.Builder builder = DictProtocol.DictGetRequest.newBuilder();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.DICT_GET)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_GET, request);
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
        .setRequestType(RequestType.DICT_PUT_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_PUT_ITEM, request);
  }

  @Override
  public void enterDictGetItem(DistkvNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictGetItemRequest.Builder builder = DictProtocol.DictGetItemRequest.newBuilder();
    builder.setItemKey(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.DICT_GET_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_GET_ITEM, request);
  }

  @Override
  public void enterDictPopItem(DistkvNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictPopItemRequest.Builder builder = DictProtocol.DictPopItemRequest.newBuilder();
    builder.setItemKey(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.DICT_POP_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_POP_ITEM, request);
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
        .setRequestType(RequestType.DICT_REMOVE_ITEM)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_REMOVE_ITEM, request);
  }

  @Override
  public void enterDictDrop(DistkvNewSQLParser.DictDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.DICT_DROP)
        .setRequest(Any.pack(builder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.DICT_DROP, request);
  }

  @Override
  public void enterSlistPut(DistkvNewSQLParser.SlistPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistPutRequest.Builder slistPutRequestBuilder =
        SortedListProtocol.SlistPutRequest.newBuilder();
    final ParseTree sortedListEntityPairsParseTree = ctx.children.get(2);
    final int sortedListEntityPairs = sortedListEntityPairsParseTree.getChildCount();
    for (int i = 0; i < sortedListEntityPairs; i++) {
      final SortedListProtocol.SortedListEntity.Builder slistBuilder =
          SortedListProtocol.SortedListEntity.newBuilder();
      final ParseTree sortedListEntityParseTree =
          sortedListEntityPairsParseTree.getChild(i);
      Preconditions.checkState(sortedListEntityParseTree.getChildCount() == 2);
      slistBuilder.setScore(Integer.parseInt(sortedListEntityParseTree.getChild(1).getText()));
      slistBuilder.setMember(sortedListEntityParseTree.getChild(0).getText());
      slistPutRequestBuilder.addList(slistBuilder);
    }
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SORTED_LIST_PUT)
        .setRequest(Any.pack(slistPutRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        RequestType.SORTED_LIST_PUT, request);
  }

  @Override
  public void enterSlistTop(DistkvNewSQLParser.SlistTopContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistTopRequest.Builder slistTopRequestBuilder =
        SortedListProtocol.SlistTopRequest.newBuilder();
    slistTopRequestBuilder.setCount(Integer.parseInt(ctx.children.get(2).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SORTED_LIST_TOP)
        .setRequest(Any.pack(slistTopRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(
        RequestType.SORTED_LIST_TOP, request);
  }

  @Override
  public void enterSlistIncrScoreDefault(DistkvNewSQLParser.SlistIncrScoreDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    SortedListProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(1);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(RequestType.SORTED_LIST_INCR_SCORE)
        .setRequest(Any.pack(slistIncrScoreRequest.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_INCR_SCORE,
        request);
  }

  @Override
  public void enterSlistIncrScoreDelta(DistkvNewSQLParser.SlistIncrScoreDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(Integer.parseInt(ctx.children.get(2).getText()));
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(0).getText())
        .setRequestType(RequestType.SORTED_LIST_INCR_SCORE)
        .setRequest(Any.pack(slistIncrScoreRequest.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_INCR_SCORE,
        request);
  }

  @Override
  public void enterSlistPutMember(DistkvNewSQLParser.SlistPutMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);

    SortedListProtocol.SlistPutMemberRequest.Builder slistPutMemberRequestBuilder =
        SortedListProtocol.SlistPutMemberRequest.newBuilder();
    slistPutMemberRequestBuilder.setScore(Integer.parseInt(ctx.children.get(3).getText()));
    slistPutMemberRequestBuilder.setMember(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SORTED_LIST_PUT_MEMBER)
        .setRequest(Any.pack(slistPutMemberRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_PUT_MEMBER,
        request);
  }

  @Override
  public void enterSlistRemoveMember(DistkvNewSQLParser.SlistRemoveMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistRemoveMemberRequest.Builder removeMemberRequestBuilder =
        SortedListProtocol.SlistRemoveMemberRequest.newBuilder();
    removeMemberRequestBuilder.setMember(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SORTED_LIST_REMOVE_MEMBER)
        .setRequest(Any.pack(removeMemberRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_REMOVE_MEMBER,
        request);
  }

  @Override
  public void enterSlistDrop(DistkvNewSQLParser.SlistDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SORTED_LIST_DROP)
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_DROP, request);
  }

  @Override
  public void enterSlistGetMember(DistkvNewSQLParser.SlistGetMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistGetMemberRequest.Builder getMemberRequestBuilder =
        SortedListProtocol.SlistGetMemberRequest.newBuilder();
    getMemberRequestBuilder.setMember(ctx.children.get(2).getText());
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(ctx.children.get(1).getText())
        .setRequestType(RequestType.SORTED_LIST_GET_MEMBER)
        .setRequest(Any.pack(getMemberRequestBuilder.build()))
        .build();
    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_GET_MEMBER,
        request);
  }
}
