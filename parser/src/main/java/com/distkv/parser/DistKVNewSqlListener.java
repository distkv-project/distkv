package com.distkv.parser;

import com.distkv.common.RequestTypeEnum;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.google.common.base.Preconditions;
import org.antlr.v4.runtime.tree.ParseTree;
import com.distkv.parser.generated.DistKVNewSQLBaseListener;
import com.distkv.parser.generated.DistKVNewSQLParser;
import com.distkv.parser.po.DistKVParsedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class DistKVNewSqlListener extends DistKVNewSQLBaseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DistKVNewSqlListener.class);

  private DistKVParsedResult parsedResult = null;

  public DistKVParsedResult getParsedResult() {
    return parsedResult;
  }

  @Override
  public void enterExit(DistKVNewSQLParser.ExitContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);
    parsedResult = new DistKVParsedResult(RequestTypeEnum.EXIT, null);
  }

  @Override
  public void enterStrPut(DistKVNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.StrPutRequest.Builder builder = StringProtocol.StrPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.STR_PUT, builder.build());
  }

  @Override
  public void enterStrGet(DistKVNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    StringProtocol.StrGetRequest.Builder builder = StringProtocol.StrGetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.STR_GET, builder.build());
  }

  @Override
  public void enterStrDrop(DistKVNewSQLParser.StrDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.STR_DROP, builder.build());
  }

  @Override
  public void enterListPut(DistKVNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListPutRequest.Builder builder = ListProtocol.ListPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_PUT, builder.build());
  }

  @Override
  public void enterListLput(DistKVNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.ListLPutRequest.Builder builder = ListProtocol.ListLPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_LPUT, builder.build());
  }

  @Override
  public void enterListRput(DistKVNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.ListRPutRequest.Builder builder = ListProtocol.ListRPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_RPUT, builder.build());
  }

  @Override
  public void enterListGetAll(DistKVNewSQLParser.ListGetAllContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);

    ListProtocol.ListGetRequest.Builder getRequestBuilder =
        ListProtocol.ListGetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);

    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetOne(DistKVNewSQLParser.ListGetOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.ListGetRequest.Builder getRequestBuilder = ListProtocol.ListGetRequest
        .newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setIndex(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_ONE);

    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetRange(DistKVNewSQLParser.ListGetRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListGetRequest.Builder getRequestBuilder = ListProtocol.ListGetRequest
        .newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setFrom(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setEnd(Integer.valueOf(ctx.getChild(2).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_RANGE);

    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListRemoveOne(DistKVNewSQLParser.ListRemoveOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.ListRemoveRequest.Builder removeRequestBuilder =
        ListProtocol.ListRemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setIndex(Integer.valueOf(ctx.children.get(1).getText()));
    parsedResult = new DistKVParsedResult(
        RequestTypeEnum.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListRemoveRange(DistKVNewSQLParser.ListRemoveRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.ListRemoveRequest.Builder removeRequestBuilder =
        ListProtocol.ListRemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setFrom(Integer.valueOf(ctx.children.get(1).getText()));
    removeRequestBuilder.setEnd(Integer.valueOf(ctx.children.get(2).getText()));
    parsedResult = new DistKVParsedResult(
        RequestTypeEnum.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListMRemove(DistKVNewSQLParser.ListMRemoveContext ctx) {
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

    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_M_REMOVE, mremoveRequest.build());
  }

  @Override
  public void enterListDrop(DistKVNewSQLParser.ListDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.LIST_DROP, builder.build());
  }

  @Override
  public void enterSetPut(DistKVNewSQLParser.SetPutContext ctx) {
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
    parsedResult = new DistKVParsedResult(RequestTypeEnum.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DistKVNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.SetGetRequest.Builder builder = SetProtocol.SetGetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.SET_GET, builder.build());
  }

  @Override
  public void enterSetDrop(DistKVNewSQLParser.SetDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.SET_DROP, builder.build());
  }

  @Override
  public void enterSetPutItem(DistKVNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetPutItemRequest.Builder builder = SetProtocol.SetPutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.SET_PUT_ITEM, builder.build());
  }

  @Override
  public void enterSetRemoveItem(DistKVNewSQLParser.SetRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.getChildCount() == 3);
    SetProtocol.SetRemoveItemRequest.Builder builder = SetProtocol.SetRemoveItemRequest
        .newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.SET_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterSetExists(DistKVNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.SetExistsRequest.Builder builder = SetProtocol.SetExistsRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setEntity(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.SET_EXIST, builder.build());
  }

  @Override
  public void enterDictPut(DistKVNewSQLParser.DictPutContext ctx) {
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
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_PUT, builder.build());
  }

  @Override
  public void enterDictGet(DistKVNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.DictGetRequest.Builder builder = DictProtocol.DictGetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_GET, builder.build());
  }

  @Override
  public void enterDictPutItem(DistKVNewSQLParser.DictPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);
    DictProtocol.DictPutItemRequest.Builder builder = DictProtocol.DictPutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    builder.setItemValue(ctx.children.get(3).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_PUT_ITEM, builder.build());
  }

  @Override
  public void enterDictGetItem(DistKVNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictGetItemRequest.Builder builder
        = DictProtocol.DictGetItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_GET_ITEM, builder.build());
  }

  @Override
  public void enterDictPopItem(DistKVNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictPopItemRequest.Builder builder = DictProtocol.DictPopItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_POP_ITEM, builder.build());
  }

  @Override
  public void enterDictRemoveItem(DistKVNewSQLParser.DictRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.DictRemoveItemRequest.Builder builder = DictProtocol.DictRemoveItemRequest
        .newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterDictDrop(DistKVNewSQLParser.DictDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistKVParsedResult(RequestTypeEnum.DICT_DROP, builder.build());
  }

  @Override
  public void enterSlistPut(DistKVNewSQLParser.SlistPutContext ctx) {
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

    parsedResult = new DistKVParsedResult(
        RequestTypeEnum.SLIST_PUT, slistPutRequestBuilder.build());
  }

  @Override
  public void enterSlistTop(DistKVNewSQLParser.SlistTopContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistTopRequest.Builder slistTopRequestBuilder =
        SortedListProtocol.SlistTopRequest.newBuilder();
    slistTopRequestBuilder.setKey(ctx.children.get(1).getText());
    slistTopRequestBuilder.setCount(Integer.valueOf(ctx.children.get(2).getText()));

    parsedResult = new DistKVParsedResult(
        RequestTypeEnum.SLIST_TOP, slistTopRequestBuilder.build());
  }

  @Override
  public void enterSlistIncrScoreDefault(DistKVNewSQLParser.SlistIncrScoreDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    SortedListProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(1);

    parsedResult = new DistKVParsedResult(RequestTypeEnum.SLIST_INCR_SCORE,
        slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistIncrScoreDelta(DistKVNewSQLParser.SlistIncrScoreDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistIncrScoreRequest.Builder slistIncrScoreRequest =
        SortedListProtocol.SlistIncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(Integer.valueOf(ctx.children.get(2).getText()));

    parsedResult = new DistKVParsedResult(RequestTypeEnum.SLIST_INCR_SCORE,
        slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistPutMember(DistKVNewSQLParser.SlistPutMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);

    SortedListProtocol.SlistPutMemberRequest.Builder slistPutMemberRequestBuilder =
        SortedListProtocol.SlistPutMemberRequest.newBuilder();
    slistPutMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    slistPutMemberRequestBuilder.setScore(Integer.valueOf(ctx.children.get(3).getText()));
    slistPutMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistKVParsedResult(RequestTypeEnum.SLIST_PUT_MEMBER,
        slistPutMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistRemoveMember(DistKVNewSQLParser.SlistRemoveMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistRemoveMemberRequest.Builder removeMemberRequestBuilder =
        SortedListProtocol.SlistRemoveMemberRequest.newBuilder();
    removeMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    removeMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistKVParsedResult(RequestTypeEnum.SLIST_REMOVE_MEMBER,
        removeMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistDrop(DistKVNewSQLParser.SlistDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder dropRequestBuilder =
        CommonProtocol.DropRequest.newBuilder();
    dropRequestBuilder.setKey(ctx.children.get(1).getText());

    parsedResult = new DistKVParsedResult(RequestTypeEnum.SLIST_DROP, dropRequestBuilder.build());
  }

  @Override
  public void enterSlistGetMember(DistKVNewSQLParser.SlistGetMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.SlistGetMemberRequest.Builder getMemberRequestBuilder =
        SortedListProtocol.SlistGetMemberRequest.newBuilder();
    getMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    getMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistKVParsedResult(RequestTypeEnum.SLIST_GET_MEMBER,
        getMemberRequestBuilder.build());
  }
}
