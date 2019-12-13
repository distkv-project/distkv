package com.distkv.dst.client.commandlinetool;

import com.distkv.dst.client.DstClient;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.parser.po.DstParsedResult;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.protobuf.generated.ListProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.protobuf.generated.SortedListProtocol;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

public class DstCommandExecutor {

  private static final String STATUS_OK = "ok";

  DstClient dstClient;

  public DstCommandExecutor(DstClient dstClient) {
    this.dstClient = dstClient;
  }

  public String execute(DstParsedResult parsedResult) {
    switch (parsedResult.getRequestType()) {
      case STR_PUT:
        CommandExecutorHandler.strPut(dstClient, parsedResult);
        return STATUS_OK;
      case STR_GET:
        StringProtocol.GetRequest getRequestStr =
                (StringProtocol.GetRequest) parsedResult.getRequest();
        return dstClient.strs().get(getRequestStr.getKey());
      case STR_DROP:
        CommonProtocol.DropRequest dropRequestStr =
                (CommonProtocol.DropRequest) parsedResult.getRequest();
        dstClient.strs().drop(dropRequestStr.getKey());
        return STATUS_OK;
      case LIST_PUT:
        ListProtocol.PutRequest putRequestList =
                (ListProtocol.PutRequest) parsedResult.getRequest();
        dstClient.lists().put(putRequestList.getKey(), putRequestList.getValuesList());
        return STATUS_OK;
      case LIST_GET:
        ListProtocol.GetRequest getRequestList =
                (ListProtocol.GetRequest) parsedResult.getRequest();
        List<String> list = null;
        if (getRequestList.getType() == ListProtocol.GetType.GET_ALL) {
          list = dstClient.lists().get(getRequestList.getKey());
        } else if (getRequestList.getType() == ListProtocol.GetType.GET_ONE) {
          list = dstClient.lists().get(getRequestList.getKey(), getRequestList.getIndex());
        } else if (getRequestList.getType() == ListProtocol.GetType.GET_RANGE) {
          list = dstClient.lists().get(getRequestList.getKey(),
                  getRequestList.getFrom(), getRequestList.getEnd());
        }
        return list.toString();
      case LIST_LPUT:
        ListProtocol.PutRequest lputRequestList =
                (ListProtocol.PutRequest) parsedResult.getRequest();
        dstClient.lists().lput(lputRequestList.getKey(), lputRequestList.getValuesList());
        return STATUS_OK;
      case LIST_RPUT:
        ListProtocol.PutRequest rputRequestList =
                (ListProtocol.PutRequest) parsedResult.getRequest();
        dstClient.lists().rput(rputRequestList.getKey(), rputRequestList.getValuesList());
        return STATUS_OK;
      case LIST_REMOVE:
        ListProtocol.RemoveRequest removeRequestList =
                (ListProtocol.RemoveRequest) parsedResult.getRequest();
        if (removeRequestList.getType() == ListProtocol.RemoveType.RemoveOne) {
          dstClient.lists().remove(removeRequestList.getKey(), removeRequestList.getIndex());
          return STATUS_OK;
        } else if (removeRequestList.getType() == ListProtocol.RemoveType.RemoveRange) {
          dstClient.lists().remove(removeRequestList.getKey(),
                  removeRequestList.getFrom(), removeRequestList.getEnd());
          return STATUS_OK;
        }
        break;
      case LIST_M_REMOVE:
        ListProtocol.MRemoveRequest multipleRemoveRequestList =
                (ListProtocol.MRemoveRequest) parsedResult.getRequest();
        dstClient.lists().multipleRemove(multipleRemoveRequestList.getKey(),
                multipleRemoveRequestList.getIndexesList());
        return STATUS_OK;
      case LIST_DROP:
        CommonProtocol.DropRequest dropReqeustList =
                (CommonProtocol.DropRequest) parsedResult.getRequest();
        dstClient.lists().drop(dropReqeustList.getKey());
        return STATUS_OK;
      case SET_PUT:
        SetProtocol.PutRequest putRequestSet =
                (SetProtocol.PutRequest) parsedResult.getRequest();
        Set<String> values = new HashSet(putRequestSet.getValuesList());
        dstClient.sets().put(putRequestSet.getKey(), values);
        return STATUS_OK;
      case SET_GET:
        SetProtocol.GetRequest getRequestSet =
                (SetProtocol.GetRequest) parsedResult.getRequest();
        return dstClient.sets().get(getRequestSet.getKey()).toString();
      case SET_DROP:
        CommonProtocol.DropRequest dropRequestSet =
                (CommonProtocol.DropRequest) parsedResult.getRequest();
        dstClient.sets().drop(dropRequestSet.getKey());
        return STATUS_OK;
      case SET_PUT_ITEM:
        SetProtocol.PutItemRequest putItemRequestSet =
                (SetProtocol.PutItemRequest) parsedResult.getRequest();
        dstClient.sets().putItem(putItemRequestSet.getKey(), putItemRequestSet.getItemValue());
        return STATUS_OK;
      case SET_REMOVE_ITEM:
        SetProtocol.RemoveItemRequest removeItemRequestSet =
                (SetProtocol.RemoveItemRequest) parsedResult.getRequest();
        dstClient.sets().removeItem(removeItemRequestSet.getKey(),
                removeItemRequestSet.getItemValue());
        return STATUS_OK;
      case SET_EXIST:
        SetProtocol.ExistsRequest existsRequestSet =
                (SetProtocol.ExistsRequest) parsedResult.getRequest();
        return String.valueOf(dstClient.sets().exists(existsRequestSet.getKey(),
                existsRequestSet.getEntity()));
      case DICT_PUT:
        DictProtocol.PutRequest putRequestDict =
                (DictProtocol.PutRequest) parsedResult.getRequest();
        DictProtocol.DstDict dict = putRequestDict.getDict();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < dict.getKeysCount(); i++) {
          map.put(dict.getKeys(i), dict.getValues(i));
        }
        dstClient.dicts().put(putRequestDict.getKey(), map);
        return STATUS_OK;
      case DICT_GET:
        DictProtocol.GetRequest getRequestDict =
                (DictProtocol.GetRequest) parsedResult.getRequest();
        return dstClient.dicts().get(getRequestDict.getKey()).toString();
      case DICT_PUT_ITEM:
        DictProtocol.PutItemRequest putItemRequestDict =
                (DictProtocol.PutItemRequest) parsedResult.getRequest();
        dstClient.dicts().putItem(putItemRequestDict.getKey(),
                putItemRequestDict.getItemKey(), putItemRequestDict.getItemValue());
        return STATUS_OK;
      case DICT_GET_ITEM:
        DictProtocol.GetItemRequest getItemRequestDict =
                (DictProtocol.GetItemRequest) parsedResult.getRequest();
        return dstClient.dicts().getItem(getItemRequestDict.getKey(),
                getItemRequestDict.getItemKey());
      case DICT_POP_ITEM:
        DictProtocol.PopItemRequest popItemRequestDict =
                (DictProtocol.PopItemRequest) parsedResult.getRequest();
        return dstClient.dicts().popItem(popItemRequestDict.getKey(),
                popItemRequestDict.getItemKey());
      case DICT_REMOVE_ITEM:
        DictProtocol.RemoveItemRequest removeItemRequestDict =
                (DictProtocol.RemoveItemRequest) parsedResult.getRequest();
        dstClient.dicts().removeItem(removeItemRequestDict.getKey(),
                removeItemRequestDict.getItemKey());
        return STATUS_OK;
      case DICT_DROP:
        CommonProtocol.DropRequest dropRequestDict =
                (CommonProtocol.DropRequest) parsedResult.getRequest();
        dstClient.dicts().drop(dropRequestDict.getKey());
        return STATUS_OK;
      case SLIST_PUT:
        SortedListProtocol.PutRequest putRequestSlist =
                (SortedListProtocol.PutRequest) parsedResult.getRequest();
        final LinkedList<SortedListEntity> sortedListEntitiesResult = new LinkedList<>();

        final List<SortedListProtocol.SortedListEntity> sortedListEntities
                = putRequestSlist.getListList();
        for (SortedListProtocol.SortedListEntity sortedListEntity : sortedListEntities) {
          final String sortedListEntityMember = sortedListEntity.getMember();
          final int sortedListEntityScore = sortedListEntity.getScore();
          sortedListEntitiesResult.add(new SortedListEntity(sortedListEntityMember,
                  sortedListEntityScore));
        }
        dstClient.sortedLists().put(putRequestSlist.getKey(),
                sortedListEntitiesResult);
        return STATUS_OK;
      case SLIST_TOP:
        SortedListProtocol.TopRequest topRequestSlist =
                (SortedListProtocol.TopRequest) parsedResult.getRequest();
        return dstClient.sortedLists().top(topRequestSlist.getKey(),
                topRequestSlist.getCount()).toString();
      case SLIST_INCR_SCORE:
        SortedListProtocol.IncrScoreRequest incrScoreRequest =
                (SortedListProtocol.IncrScoreRequest) parsedResult.getRequest();
        dstClient.sortedLists().incrItem(incrScoreRequest.getKey(),
                incrScoreRequest.getMember(), incrScoreRequest.getDelta());
        return STATUS_OK;
      case SLIST_PUT_MEMBER:
        SortedListProtocol.PutMemberRequest putMemberRequest =
                (SortedListProtocol.PutMemberRequest) parsedResult.getRequest();
        final String member = putMemberRequest.getMember();
        final int score = putMemberRequest.getScore();
        dstClient.sortedLists().putItem(putMemberRequest.getKey(),
                new SortedListEntity(member, score));
        return STATUS_OK;
      case SLIST_REMOVE_MEMBER:
        SortedListProtocol.RemoveMemberRequest removeMemberRequest =
                (SortedListProtocol.RemoveMemberRequest) parsedResult.getRequest();
        dstClient.sortedLists().removeItem(removeMemberRequest.getKey(),
                removeMemberRequest.getMember());
        return STATUS_OK;
      case SLIST_DROP:
        CommonProtocol.DropRequest dropRequest =
                (CommonProtocol.DropRequest) parsedResult.getRequest();
        dstClient.sortedLists().drop(dropRequest.getKey());
        return STATUS_OK;
      default:
        return null;
    }
    return null;
  }
}
