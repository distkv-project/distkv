package com.distkv.parser;

import com.distkv.parser.generated.DistkvNewSQLBaseListener;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.google.common.base.Preconditions;
import org.antlr.v4.runtime.tree.ParseTree;
import com.distkv.parser.generated.DistkvNewSQLParser;
import com.distkv.parser.po.DistkvParsedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class DistkvNewSqlListener extends DistkvNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DistkvNewSqlListener.class);

  private DistkvParsedResult parsedResult = null;

  public DistkvParsedResult getParsedResult() {
    return parsedResult;
  }

  @Override
  public void enterExit(DistkvNewSQLParser.ExitContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);
    parsedResult = new DistkvParsedResult(RequestType.SET_EXISTS, null);
  }

  @Override
  public void enterStrPut(DistkvNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.StrPutRequest.Builder builder = StringProtocol.StrPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.STR_PUT, builder.build());
  }

  @Override
  public void enterStrGet(DistkvNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    StringProtocol.StrGetRequest.Builder builder = StringProtocol.StrGetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.STR_GET, builder.build());
  }

  @Override
  public void enterStrDrop(DistkvNewSQLParser.StrDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.STR_DROP, builder.build());
  }

  @Override
  public void enterListPut(DistkvNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListPutRequest.Builder builder = ListProtocol.ListPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistkvParsedResult(RequestType.LIST_PUT, builder.build());
  }

  @Override
  public void enterListLput(DistkvNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.ListLPutRequest.Builder builder = ListProtocol.ListLPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistkvParsedResult(RequestType.LIST_LPUT, builder.build());
  }

  @Override
  public void enterListRput(DistkvNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.ListRPutRequest.Builder builder = ListProtocol.ListRPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistkvParsedResult(RequestType.LIST_RPUT, builder.build());
  }

  @Override
  public void enterListGetAll(DistkvNewSQLParser.ListGetAllContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);

    ListProtocol.ListGetRequest.Builder getRequestBuilder =
        ListProtocol.ListGetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);

    parsedResult = new DistkvParsedResult(RequestType.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetOne(DistkvNewSQLParser.ListGetOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.ListGetRequest.Builder getRequestBuilder = ListProtocol.ListGetRequest
        .newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setIndex(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_ONE);

    parsedResult = new DistkvParsedResult(RequestType.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetRange(DistkvNewSQLParser.ListGetRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListGetRequest.Builder getRequestBuilder = ListProtocol.ListGetRequest
        .newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setFrom(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setEnd(Integer.valueOf(ctx.getChild(2).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_RANGE);

    parsedResult = new DistkvParsedResult(RequestType.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListRemoveOne(DistkvNewSQLParser.ListRemoveOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.ListRemoveRequest.Builder removeRequestBuilder =
        ListProtocol.ListRemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setIndex(Integer.valueOf(ctx.children.get(1).getText()));
    parsedResult = new DistkvParsedResult(
        RequestType.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListRemoveRange(DistkvNewSQLParser.ListRemoveRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListRemoveRequest.Builder removeRequestBuilder =
        ListProtocol.ListRemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setFrom(Integer.valueOf(ctx.children.get(1).getText()));
    removeRequestBuilder.setEnd(Integer.valueOf(ctx.children.get(2).getText()));
    parsedResult = new DistkvParsedResult(
        RequestType.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListMRemove(DistkvNewSQLParser.ListMRemoveContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() >= 3);

    ListProtocol.ListMRemoveRequest.Builder mremoveRequest = ListProtocol.ListMRemoveRequest
        .newBuilder();
    mremoveRequest.setKey(ctx.children.get(1).getText());

    List<Integer> indexesList = new ArrayList<>();
    for (int i = 2; i < ctx.children.size(); i++) {
      indexesList.add(Integer.valueOf(ctx.children.get(i).getText()));
    }
    mremoveRequest.addAllIndexes(indexesList);

    parsedResult = new DistkvParsedResult(RequestType.LIST_MREMOVE, mremoveRequest.build());
  }

  @Override
  public void enterListDrop(DistkvNewSQLParser.ListDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.LIST_DROP, builder.build());
  }

  @Override
  public void enterSetPut(DistkvNewSQLParser.SetPutContext ctx) {
    // The children of the `set_put` should be 3:
    //        `set.put    key     value_array`
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SetProtocol.SetPutRequest.Builder builder = SetProtocol.SetPutRequest.newBuilder();
    final String key = ctx.children.get(1).getText();
    builder.setKey(key);
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    SetProtocol.SetPutRequest request = builder.build();
    parsedResult = new DistkvParsedResult(RequestType.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DistkvNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.SetGetRequest.Builder builder = SetProtocol.SetGetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.SET_GET, builder.build());
  }

  @Override
  public void enterSetDrop(DistkvNewSQLParser.SetDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.SET_DROP, builder.build());
  }

  @Override
  public void enterSetPutItem(DistkvNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetPutItemRequest.Builder builder = SetProtocol.SetPutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.SET_PUT_ITEM, builder.build());
  }

  @Override
  public void enterSetRemoveItem(DistkvNewSQLParser.SetRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.getChildCount() == 3);
    SetProtocol.SetRemoveItemRequest.Builder builder = SetProtocol.SetRemoveItemRequest
        .newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.SET_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterSetExists(DistkvNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetExistsRequest.Builder builder = SetProtocol.SetExistsRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setEntity(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.SET_EXISTS, builder.build());
  }

  @Override
  public void enterDictPut(DistkvNewSQLParser.DictPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictPutRequest.Builder builder = DictProtocol.DictPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
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
    parsedResult = new DistkvParsedResult(RequestType.DICT_PUT, builder.build());
  }

  @Override
  public void enterDictGet(DistkvNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.DictGetRequest.Builder builder = DictProtocol.DictGetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.DICT_GET, builder.build());
  }

  @Override
  public void enterDictPutItem(DistkvNewSQLParser.DictPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);
    DictProtocol.DictPutItemRequest.Builder builder = DictProtocol.DictPutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    builder.setItemValue(ctx.children.get(3).getText());
    parsedResult = new DistkvParsedResult(RequestType.DICT_PUT_ITEM, builder.build());
  }

  @Override
  public void enterDictGetItem(DistkvNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictGetItemRequest.Builder builder
        = DictProtocol.DictGetItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.DICT_GET_ITEM, builder.build());
  }

  @Override
  public void enterDictPopItem(DistkvNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictPopItemRequest.Builder builder = DictProtocol.DictPopItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.DICT_POP_ITEM, builder.build());
  }

  @Override
  public void enterDictRemoveItem(DistkvNewSQLParser.DictRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictRemoveItemRequest.Builder builder = DictProtocol.DictRemoveItemRequest
        .newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestType.DICT_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterDictDrop(DistkvNewSQLParser.DictDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestType.DICT_DROP, builder.build());
  }

  @Override
  public void enterSlistPut(DistkvNewSQLParser.SlistPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistPutRequest.Builder slistPutRequestBuilder =
        SortedListProtocol.SlistPutRequest.newBuilder();
    final ParseTree sortedListEntityPairsParseTree = ctx.children.get(2);
    final int sortedListEntityPairs = sortedListEntityPairsParseTree.getChildCount();

    slistPutRequestBuilder.setKey(ctx.children.get(1).getText());
    for (int i = 0; i < sortedListEntityPairs; i++) {
      final SortedListProtocol.SortedListEntity.Builder slistBuilder =
          SortedListProtocol.SortedListEntity.newBuilder();
      final ParseTree sortedListEntityParseTree =
          sortedListEntityPairsParseTree.getChild(i);
      Preconditions.checkState(sortedListEntityParseTree.getChildCount() == 2);
      slistBuilder.setScore(Integer.valueOf(sortedListEntityParseTree.getChild(1).getText()));
      slistBuilder.setMember(sortedListEntityParseTree.getChild(0).getText());
      slistPutRequestBuilder.addList(slistBuilder);
    }

    parsedResult = new DistkvParsedResult(
        RequestType.SORTED_LIST_PUT, slistPutRequestBuilder.build());
  }

  @Override
  public void enterSlistTop(DistkvNewSQLParser.SlistTopContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistTopRequest.Builder slistTopRequestBuilder =
        SortedListProtocol.SlistTopRequest.newBuilder();
    slistTopRequestBuilder.setKey(ctx.children.get(1).getText());
    slistTopRequestBuilder.setCount(Integer.parseInt(ctx.children.get(2).getText()));

    parsedResult = new DistkvParsedResult(
        RequestType.SORTED_LIST_TOP, slistTopRequestBuilder.build());
  }

  @Override
  public void enterSlistIncrScoreDefault(DistkvNewSQLParser.SlistIncrScoreDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    SortedListProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(1);

    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_INCR_SCORE,
        slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistIncrScoreDelta(DistkvNewSQLParser.SlistIncrScoreDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(Integer.parseInt(ctx.children.get(2).getText()));

    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_INCR_SCORE,
        slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistPutMember(DistkvNewSQLParser.SlistPutMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);

    SortedListProtocol.SlistPutMemberRequest.Builder slistPutMemberRequestBuilder =
        SortedListProtocol.SlistPutMemberRequest.newBuilder();
    slistPutMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    slistPutMemberRequestBuilder.setScore(Integer.valueOf(ctx.children.get(3).getText()));
    slistPutMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_PUT_MEMBER,
        slistPutMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistRemoveMember(DistkvNewSQLParser.SlistRemoveMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistRemoveMemberRequest.Builder removeMemberRequestBuilder =
        SortedListProtocol.SlistRemoveMemberRequest.newBuilder();
    removeMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    removeMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_REMOVE_MEMBER,
        removeMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistDrop(DistkvNewSQLParser.SlistDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder dropRequestBuilder =
        CommonProtocol.DropRequest.newBuilder();
    dropRequestBuilder.setKey(ctx.children.get(1).getText());

    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_DROP, dropRequestBuilder.build());
  }

  @Override
  public void enterSlistGetMember(DistkvNewSQLParser.SlistGetMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistGetMemberRequest.Builder getMemberRequestBuilder =
        SortedListProtocol.SlistGetMemberRequest.newBuilder();
    getMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    getMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistkvParsedResult(RequestType.SORTED_LIST_GET_MEMBER,
        getMemberRequestBuilder.build());
  }
}
