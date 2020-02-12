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
import com.distkv.parser.generated.DistkvNewSQLBaseListener;
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
    parsedResult = new DistkvParsedResult(RequestTypeEnum.EXIT, null);
  }

  @Override
  public void enterStrPut(DistkvNewSQLParser.StrPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    StringProtocol.PutRequest.Builder builder = StringProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setValue(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.STR_PUT, builder.build());
  }

  @Override
  public void enterStrGet(DistkvNewSQLParser.StrGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    StringProtocol.GetRequest.Builder builder = StringProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.STR_GET, builder.build());
  }

  @Override
  public void enterStrDrop(DistkvNewSQLParser.StrDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.STR_DROP, builder.build());
  }

  @Override
  public void enterListPut(DistkvNewSQLParser.ListPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.PutRequest.Builder builder = ListProtocol.PutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_PUT, builder.build());
  }

  @Override
  public void enterListLput(DistkvNewSQLParser.ListLputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.LPutRequest.Builder builder = ListProtocol.LPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_LPUT, builder.build());
  }

  @Override
  public void enterListRput(DistkvNewSQLParser.ListRputContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    ListProtocol.RPutRequest.Builder builder = ListProtocol.RPutRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    final int valueSize = ctx.children.get(2).getChildCount();
    for (int i = 0; i < valueSize; ++i) {
      builder.addValues(ctx.children.get(2).getChild(i).getText());
    }
    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_RPUT, builder.build());
  }

  @Override
  public void enterListGetAll(DistkvNewSQLParser.ListGetAllContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 1);

    ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setType(ListProtocol.GetType.GET_ALL);

    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetOne(DistkvNewSQLParser.ListGetOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setIndex(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_ONE);

    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListGetRange(DistkvNewSQLParser.ListGetRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.GetRequest.Builder getRequestBuilder = ListProtocol.GetRequest.newBuilder();
    getRequestBuilder.setKey(ctx.getChild(0).getText());
    getRequestBuilder.setFrom(Integer.valueOf(ctx.getChild(1).getText()));
    getRequestBuilder.setEnd(Integer.valueOf(ctx.getChild(2).getText()));
    getRequestBuilder.setType(ListProtocol.GetType.GET_RANGE);

    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_GET, getRequestBuilder.build());
  }

  @Override
  public void enterListRemoveOne(DistkvNewSQLParser.ListRemoveOneContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    ListProtocol.RemoveRequest.Builder removeRequestBuilder =
            ListProtocol.RemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveOne);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setIndex(Integer.valueOf(ctx.children.get(1).getText()));
    parsedResult = new DistkvParsedResult(
        RequestTypeEnum.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListRemoveRange(DistkvNewSQLParser.ListRemoveRangeContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    ListProtocol.RemoveRequest.Builder removeRequestBuilder =
            ListProtocol.RemoveRequest.newBuilder();

    removeRequestBuilder.setType(ListProtocol.RemoveType.RemoveRange);
    removeRequestBuilder.setKey(ctx.children.get(0).getText());
    removeRequestBuilder.setFrom(Integer.valueOf(ctx.children.get(1).getText()));
    removeRequestBuilder.setEnd(Integer.valueOf(ctx.children.get(2).getText()));
    parsedResult = new DistkvParsedResult(
        RequestTypeEnum.LIST_REMOVE, removeRequestBuilder.build());
  }

  @Override
  public void enterListMRemove(DistkvNewSQLParser.ListMRemoveContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() >= 3);

    ListProtocol.MRemoveRequest.Builder mremoveRequest = ListProtocol.MRemoveRequest.newBuilder();
    mremoveRequest.setKey(ctx.children.get(1).getText());

    List<Integer> indexesList = new ArrayList<>();
    for (int i = 2; i < ctx.children.size(); i++) {
      indexesList.add(Integer.valueOf(ctx.children.get(i).getText()));
    }
    mremoveRequest.addAllIndexes(indexesList);

    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_M_REMOVE, mremoveRequest.build());
  }

  @Override
  public void enterListDrop(DistkvNewSQLParser.ListDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.LIST_DROP, builder.build());
  }

  @Override
  public void enterSetPut(DistkvNewSQLParser.SetPutContext ctx) {
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
    parsedResult = new DistkvParsedResult(RequestTypeEnum.SET_PUT, request);
  }

  @Override
  public void enterSetGet(DistkvNewSQLParser.SetGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    SetProtocol.GetRequest.Builder builder = SetProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.SET_GET, builder.build());
  }

  @Override
  public void enterSetDrop(DistkvNewSQLParser.SetDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.SET_DROP, builder.build());
  }

  @Override
  public void enterSetPutItem(DistkvNewSQLParser.SetPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.PutItemRequest.Builder builder = SetProtocol.PutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.SET_PUT_ITEM, builder.build());
  }

  @Override
  public void enterSetRemoveItem(DistkvNewSQLParser.SetRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.getChildCount() == 3);
    SetProtocol.RemoveItemRequest.Builder builder = SetProtocol.RemoveItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemValue(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.SET_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterSetExists(DistkvNewSQLParser.SetExistsContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    SetProtocol.ExistsRequest.Builder builder = SetProtocol.ExistsRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setEntity(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.SET_EXIST, builder.build());
  }

  @Override
  public void enterDictPut(DistkvNewSQLParser.DictPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.PutRequest.Builder builder = DictProtocol.PutRequest.newBuilder();
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
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_PUT, builder.build());
  }

  @Override
  public void enterDictGet(DistkvNewSQLParser.DictGetContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    DictProtocol.GetRequest.Builder builder = DictProtocol.GetRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_GET, builder.build());
  }

  @Override
  public void enterDictPutItem(DistkvNewSQLParser.DictPutItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);
    DictProtocol.PutItemRequest.Builder builder = DictProtocol.PutItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    builder.setItemValue(ctx.children.get(3).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_PUT_ITEM, builder.build());
  }

  @Override
  public void enterDictGetItem(DistkvNewSQLParser.DictGetItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.GetItemRequest.Builder builder
        = DictProtocol.GetItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_GET_ITEM, builder.build());
  }

  @Override
  public void enterDictPopItem(DistkvNewSQLParser.DictPopItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.PopItemRequest.Builder builder = DictProtocol.PopItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_POP_ITEM, builder.build());
  }

  @Override
  public void enterDictRemoveItem(DistkvNewSQLParser.DictRemoveItemContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);
    DictProtocol.RemoveItemRequest.Builder builder = DictProtocol.RemoveItemRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    builder.setItemKey(ctx.children.get(2).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_REMOVE_ITEM, builder.build());
  }

  @Override
  public void enterDictDrop(DistkvNewSQLParser.DictDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);
    CommonProtocol.DropRequest.Builder builder = CommonProtocol.DropRequest.newBuilder();
    builder.setKey(ctx.children.get(1).getText());
    parsedResult = new DistkvParsedResult(RequestTypeEnum.DICT_DROP, builder.build());
  }

  @Override
  public void enterSlistPut(DistkvNewSQLParser.SlistPutContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.PutRequest.Builder slistPutRequestBuilder =
            SortedListProtocol.PutRequest.newBuilder();
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
        RequestTypeEnum.SLIST_PUT, slistPutRequestBuilder.build());
  }

  @Override
  public void enterSlistTop(DistkvNewSQLParser.SlistTopContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.TopRequest.Builder slistTopRequestBuilder =
            SortedListProtocol.TopRequest.newBuilder();
    slistTopRequestBuilder.setKey(ctx.children.get(1).getText());
    slistTopRequestBuilder.setCount(Integer.valueOf(ctx.children.get(2).getText()));

    parsedResult = new DistkvParsedResult(
        RequestTypeEnum.SLIST_TOP, slistTopRequestBuilder.build());
  }

  @Override
  public void enterSlistIncrScoreDefault(DistkvNewSQLParser.SlistIncrScoreDefaultContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    SortedListProtocol.IncrScoreRequest.Builder slistIncrScoreRequest =
            SortedListProtocol.IncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(1);

    parsedResult = new DistkvParsedResult(RequestTypeEnum.SLIST_INCR_SCORE,
            slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistIncrScoreDelta(DistkvNewSQLParser.SlistIncrScoreDeltaContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.IncrScoreRequest.Builder slistIncrScoreRequest =
            SortedListProtocol.IncrScoreRequest.newBuilder();
    slistIncrScoreRequest.setKey(ctx.children.get(0).getText());
    slistIncrScoreRequest.setMember(ctx.children.get(1).getText());
    slistIncrScoreRequest.setDelta(Integer.valueOf(ctx.children.get(2).getText()));

    parsedResult = new DistkvParsedResult(RequestTypeEnum.SLIST_INCR_SCORE,
            slistIncrScoreRequest.build());
  }

  @Override
  public void enterSlistPutMember(DistkvNewSQLParser.SlistPutMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 4);

    SortedListProtocol.PutMemberRequest.Builder slistPutMemberRequestBuilder =
            SortedListProtocol.PutMemberRequest.newBuilder();
    slistPutMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    slistPutMemberRequestBuilder.setScore(Integer.valueOf(ctx.children.get(3).getText()));
    slistPutMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistkvParsedResult(RequestTypeEnum.SLIST_PUT_MEMBER,
            slistPutMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistRemoveMember(DistkvNewSQLParser.SlistRemoveMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.RemoveMemberRequest.Builder removeMemberRequestBuilder =
            SortedListProtocol.RemoveMemberRequest.newBuilder();
    removeMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    removeMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistkvParsedResult(RequestTypeEnum.SLIST_REMOVE_MEMBER,
            removeMemberRequestBuilder.build());
  }

  @Override
  public void enterSlistDrop(DistkvNewSQLParser.SlistDropContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 2);

    CommonProtocol.DropRequest.Builder dropRequestBuilder =
            CommonProtocol.DropRequest.newBuilder();
    dropRequestBuilder.setKey(ctx.children.get(1).getText());

    parsedResult = new DistkvParsedResult(RequestTypeEnum.SLIST_DROP, dropRequestBuilder.build());
  }

  @Override
  public void enterSlistGetMember(DistkvNewSQLParser.SlistGetMemberContext ctx) {
    Preconditions.checkState(parsedResult == null);
    Preconditions.checkState(ctx.children.size() == 3);

    SortedListProtocol.GetMemberRequest.Builder getMemberRequestBuilder =
            SortedListProtocol.GetMemberRequest.newBuilder();
    getMemberRequestBuilder.setKey(ctx.children.get(1).getText());
    getMemberRequestBuilder.setMember(ctx.children.get(2).getText());

    parsedResult = new DistkvParsedResult(RequestTypeEnum.SLIST_GET_MEMBER,
            getMemberRequestBuilder.build());
  }
}
