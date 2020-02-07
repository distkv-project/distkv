package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandExecutorHandler {

  private static final String STATUS_OK = "ok";

  public static String strPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    StringProtocol.PutRequest putRequestStr =
        (StringProtocol.PutRequest) parsedResult.getRequest();
    distkvClient.strs().put(putRequestStr.getKey(), putRequestStr.getValue());
    return STATUS_OK;
  }

  public static String strGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    StringProtocol.GetRequest getRequestStr =
        (StringProtocol.GetRequest) parsedResult.getRequest();
    return distkvClient.strs().get(getRequestStr.getKey());
  }

  public static String strDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequestStr =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    distkvClient.strs().drop(dropRequestStr.getKey());
    return STATUS_OK;
  }

  public static String listPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    ListProtocol.PutRequest putRequestList =
        (ListProtocol.PutRequest) parsedResult.getRequest();
    distkvClient.lists().put(putRequestList.getKey(), putRequestList.getValuesList());
    return STATUS_OK;
  }

  public static String listGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    ListProtocol.GetRequest getRequestList =
        (ListProtocol.GetRequest) parsedResult.getRequest();
    List<String> list = null;
    if (getRequestList.getType() == ListProtocol.GetType.GET_ALL) {
      list = distkvClient.lists().get(getRequestList.getKey());
    } else if (getRequestList.getType() == ListProtocol.GetType.GET_ONE) {
      list = distkvClient.lists().get(getRequestList.getKey(), getRequestList.getIndex());
    } else if (getRequestList.getType() == ListProtocol.GetType.GET_RANGE) {
      list = distkvClient.lists().get(getRequestList.getKey(),
          getRequestList.getFrom(), getRequestList.getEnd());
    }
    return list.toString();
  }

  public static String listLPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    ListProtocol.LPutRequest lputRequestList =
        (ListProtocol.LPutRequest) parsedResult.getRequest();
    distkvClient.lists().lput(lputRequestList.getKey(), lputRequestList.getValuesList());
    return STATUS_OK;
  }

  public static String listRPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    ListProtocol.RPutRequest rputRequestList =
        (ListProtocol.RPutRequest) parsedResult.getRequest();
    distkvClient.lists().rput(rputRequestList.getKey(), rputRequestList.getValuesList());
    return STATUS_OK;
  }

  public static String listRemove(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    ListProtocol.RemoveRequest removeRequestList =
        (ListProtocol.RemoveRequest) parsedResult.getRequest();
    if (removeRequestList.getType() == ListProtocol.RemoveType.RemoveOne) {
      distkvClient.lists().remove(removeRequestList.getKey(), removeRequestList.getIndex());
      return STATUS_OK;
    } else if (removeRequestList.getType() == ListProtocol.RemoveType.RemoveRange) {
      distkvClient.lists().remove(removeRequestList.getKey(),
          removeRequestList.getFrom(), removeRequestList.getEnd());
      return STATUS_OK;
    }
    return null;
  }

  public static String listMRemove(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    ListProtocol.MRemoveRequest multipleRemoveRequestList =
        (ListProtocol.MRemoveRequest) parsedResult.getRequest();
    distkvClient.lists().mremove(multipleRemoveRequestList.getKey(),
        multipleRemoveRequestList.getIndexesList());
    return STATUS_OK;
  }

  public static String listDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    CommonProtocol.DropRequest dropReqeustList =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    distkvClient.lists().drop(dropReqeustList.getKey());
    return STATUS_OK;
  }

  public static String setPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SetProtocol.PutRequest putRequestSet =
        (SetProtocol.PutRequest) parsedResult.getRequest();
    Set<String> values = new HashSet(putRequestSet.getValuesList());
    distkvClient.sets().put(putRequestSet.getKey(), values);
    return STATUS_OK;
  }

  public static String setGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SetProtocol.GetRequest getRequestSet =
        (SetProtocol.GetRequest) parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Set<String> set = distkvClient.sets().get(getRequestSet.getKey());
    boolean first = true;
    for (final String element : set) {
      if (first) {
        first = false;
      } else {
        stringBuilder.append(", ");
      }
      stringBuilder.append(element);
    }
    stringBuilder.append("}");
    return stringBuilder.toString();
  }

  public static String setDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequestSet =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    distkvClient.sets().drop(dropRequestSet.getKey());
    return STATUS_OK;
  }

  public static String setPutItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SetProtocol.PutItemRequest putItemRequestSet =
        (SetProtocol.PutItemRequest) parsedResult.getRequest();
    distkvClient.sets().putItem(putItemRequestSet.getKey(), putItemRequestSet.getItemValue());
    return STATUS_OK;
  }

  public static String setRemoveItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SetProtocol.RemoveItemRequest removeItemRequestSet =
        (SetProtocol.RemoveItemRequest) parsedResult.getRequest();
    distkvClient.sets().removeItem(removeItemRequestSet.getKey(),
        removeItemRequestSet.getItemValue());
    return STATUS_OK;
  }

  public static String setExists(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SetProtocol.ExistsRequest existsRequestSet =
        (SetProtocol.ExistsRequest) parsedResult.getRequest();
    return String.valueOf(distkvClient.sets().exists(existsRequestSet.getKey(),
        existsRequestSet.getEntity()));
  }

  public static String dictPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DictProtocol.PutRequest putRequestDict =
        (DictProtocol.PutRequest) parsedResult.getRequest();
    DictProtocol.DistKVDict dict = putRequestDict.getDict();
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < dict.getKeysCount(); i++) {
      map.put(dict.getKeys(i), dict.getValues(i));
    }
    distkvClient.dicts().put(putRequestDict.getKey(), map);
    return STATUS_OK;
  }

  public static String dictGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DictProtocol.GetRequest getRequestDict =
        (DictProtocol.GetRequest) parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Map<String, String> map = distkvClient.dicts().get(getRequestDict.getKey());
    boolean first = true;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (first) {
        first = false;
      } else {
        stringBuilder.append(",");
      }
      stringBuilder.append(" ");
      stringBuilder.append(entry.getKey());
      stringBuilder.append(" : ");
      stringBuilder.append(entry.getValue());
    }
    stringBuilder.append("}");
    return stringBuilder.toString();
  }

  public static String dictPutItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DictProtocol.PutItemRequest putItemRequestDict =
        (DictProtocol.PutItemRequest) parsedResult.getRequest();
    distkvClient.dicts().putItem(putItemRequestDict.getKey(),
        putItemRequestDict.getItemKey(), putItemRequestDict.getItemValue());
    return STATUS_OK;
  }

  public static String dictGetItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DictProtocol.GetItemRequest getItemRequestDict =
        (DictProtocol.GetItemRequest) parsedResult.getRequest();
    return distkvClient.dicts().getItem(getItemRequestDict.getKey(),
        getItemRequestDict.getItemKey());
  }

  public static String dictPopItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DictProtocol.PopItemRequest popItemRequestDict =
        (DictProtocol.PopItemRequest) parsedResult.getRequest();
    return distkvClient.dicts().popItem(popItemRequestDict.getKey(),
        popItemRequestDict.getItemKey());
  }

  public static String dictRemoveItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DictProtocol.RemoveItemRequest removeItemRequestDict =
        (DictProtocol.RemoveItemRequest) parsedResult.getRequest();
    distkvClient.dicts().removeItem(removeItemRequestDict.getKey(),
        removeItemRequestDict.getItemKey());
    return STATUS_OK;
  }

  public static String dictDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequestDict =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    distkvClient.dicts().drop(dropRequestDict.getKey());
    return STATUS_OK;
  }

  public static String slistPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
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
    distkvClient.sortedLists().put(putRequestSlist.getKey(),
        sortedListEntitiesResult);
    return STATUS_OK;
  }

  public static String slistTop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SortedListProtocol.TopRequest topRequestSlist =
        (SortedListProtocol.TopRequest) parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    LinkedList<SortedListEntity> listEntities = distkvClient.sortedLists()
            .top(topRequestSlist.getKey(), topRequestSlist.getCount());
    boolean first = true;
    stringBuilder.append("[");
    for (final SortedListEntity entity : listEntities) {
      if (first) {
        first = false;
      } else {
        stringBuilder.append(", ");
      }
      stringBuilder.append("(");
      stringBuilder.append(entity.getMember());
      stringBuilder.append(", ");
      stringBuilder.append(entity.getScore());
      stringBuilder.append(")");
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  public static String slistIncrScore(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SortedListProtocol.IncrScoreRequest incrScoreRequest =
        (SortedListProtocol.IncrScoreRequest) parsedResult.getRequest();
    distkvClient.sortedLists().incrScore(incrScoreRequest.getKey(),
        incrScoreRequest.getMember(), incrScoreRequest.getDelta());
    return STATUS_OK;
  }

  public static String slistPutMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SortedListProtocol.PutMemberRequest putMemberRequest =
        (SortedListProtocol.PutMemberRequest) parsedResult.getRequest();
    final String member = putMemberRequest.getMember();
    final int score = putMemberRequest.getScore();
    distkvClient.sortedLists().putMember(putMemberRequest.getKey(),
        new SortedListEntity(member, score));
    return STATUS_OK;
  }

  public static String slistRemoveMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SortedListProtocol.RemoveMemberRequest removeMemberRequest =
        (SortedListProtocol.RemoveMemberRequest) parsedResult.getRequest();
    distkvClient.sortedLists().removeMember(removeMemberRequest.getKey(),
        removeMemberRequest.getMember());
    return STATUS_OK;
  }

  public static String slistDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequest =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    distkvClient.sortedLists().drop(dropRequest.getKey());
    return STATUS_OK;
  }

  public static String slistGetMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    SortedListProtocol.GetMemberRequest getMemberRequest =
        (SortedListProtocol.GetMemberRequest) parsedResult.getRequest();
    final DistKVTuple<Integer, Integer> tuple = distkvClient.sortedLists().getMember(
        getMemberRequest.getKey(), getMemberRequest.getMember());
    // output: (member, score), ranking
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    stringBuilder.append(getMemberRequest.getMember());
    stringBuilder.append(", ");
    stringBuilder.append(tuple.getFirst());
    stringBuilder.append("), ");
    final int ranking = tuple.getSecond();
    stringBuilder.append(ranking);
    switch (ranking) {
      case 1:
        stringBuilder.append("st");
        break;
      case 2:
        stringBuilder.append("nd");
        break;
      case 3:
        stringBuilder.append("rd");
        break;
      default:
        stringBuilder.append("th");
    }
    return stringBuilder.toString();
  }
}
