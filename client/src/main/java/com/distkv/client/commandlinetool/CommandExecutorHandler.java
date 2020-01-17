package com.distkv.client.commandlinetool;

import com.distkv.common.DstTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.parser.po.DstParsedResult;
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

  public static String strPut(DstClient dstClient, DstParsedResult parsedResult) {
    StringProtocol.PutRequest putRequestStr =
        (StringProtocol.PutRequest) parsedResult.getRequest();
    dstClient.strs().put(putRequestStr.getKey(), putRequestStr.getValue());
    return STATUS_OK;
  }

  public static String strGet(DstClient dstClient, DstParsedResult parsedResult) {
    StringProtocol.GetRequest getRequestStr =
        (StringProtocol.GetRequest) parsedResult.getRequest();
    return dstClient.strs().get(getRequestStr.getKey());
  }

  public static String strDrop(DstClient dstClient, DstParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequestStr =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    dstClient.strs().drop(dropRequestStr.getKey());
    return STATUS_OK;
  }

  public static String listPut(DstClient dstClient, DstParsedResult parsedResult) {
    ListProtocol.PutRequest putRequestList =
        (ListProtocol.PutRequest) parsedResult.getRequest();
    dstClient.lists().put(putRequestList.getKey(), putRequestList.getValuesList());
    return STATUS_OK;
  }

  public static String listGet(DstClient dstClient, DstParsedResult parsedResult) {
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
  }

  public static String listLPut(DstClient dstClient, DstParsedResult parsedResult) {
    ListProtocol.LPutRequest lputRequestList =
        (ListProtocol.LPutRequest) parsedResult.getRequest();
    dstClient.lists().lput(lputRequestList.getKey(), lputRequestList.getValuesList());
    return STATUS_OK;
  }

  public static String listRPut(DstClient dstClient, DstParsedResult parsedResult) {
    ListProtocol.RPutRequest rputRequestList =
        (ListProtocol.RPutRequest) parsedResult.getRequest();
    dstClient.lists().rput(rputRequestList.getKey(), rputRequestList.getValuesList());
    return STATUS_OK;
  }

  public static String listRemove(DstClient dstClient, DstParsedResult parsedResult) {
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
    return null;
  }

  public static String listMRemove(DstClient dstClient, DstParsedResult parsedResult) {
    ListProtocol.MRemoveRequest multipleRemoveRequestList =
        (ListProtocol.MRemoveRequest) parsedResult.getRequest();
    dstClient.lists().mremove(multipleRemoveRequestList.getKey(),
        multipleRemoveRequestList.getIndexesList());
    return STATUS_OK;
  }

  public static String listDrop(DstClient dstClient, DstParsedResult parsedResult) {
    CommonProtocol.DropRequest dropReqeustList =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    dstClient.lists().drop(dropReqeustList.getKey());
    return STATUS_OK;
  }

  public static String setPut(DstClient dstClient, DstParsedResult parsedResult) {
    SetProtocol.PutRequest putRequestSet =
        (SetProtocol.PutRequest) parsedResult.getRequest();
    Set<String> values = new HashSet(putRequestSet.getValuesList());
    dstClient.sets().put(putRequestSet.getKey(), values);
    return STATUS_OK;
  }

  public static String setGet(DstClient dstClient, DstParsedResult parsedResult) {
    SetProtocol.GetRequest getRequestSet =
        (SetProtocol.GetRequest) parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Set<String> set = dstClient.sets().get(getRequestSet.getKey());
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

  public static String setDrop(DstClient dstClient, DstParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequestSet =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    dstClient.sets().drop(dropRequestSet.getKey());
    return STATUS_OK;
  }

  public static String setPutItem(DstClient dstClient, DstParsedResult parsedResult) {
    SetProtocol.PutItemRequest putItemRequestSet =
        (SetProtocol.PutItemRequest) parsedResult.getRequest();
    dstClient.sets().putItem(putItemRequestSet.getKey(), putItemRequestSet.getItemValue());
    return STATUS_OK;
  }

  public static String setRemoveItem(DstClient dstClient, DstParsedResult parsedResult) {
    SetProtocol.RemoveItemRequest removeItemRequestSet =
        (SetProtocol.RemoveItemRequest) parsedResult.getRequest();
    dstClient.sets().removeItem(removeItemRequestSet.getKey(),
        removeItemRequestSet.getItemValue());
    return STATUS_OK;
  }

  public static String setExists(DstClient dstClient, DstParsedResult parsedResult) {
    SetProtocol.ExistsRequest existsRequestSet =
        (SetProtocol.ExistsRequest) parsedResult.getRequest();
    return String.valueOf(dstClient.sets().exists(existsRequestSet.getKey(),
        existsRequestSet.getEntity()));
  }

  public static String dictPut(DstClient dstClient, DstParsedResult parsedResult) {
    DictProtocol.PutRequest putRequestDict =
        (DictProtocol.PutRequest) parsedResult.getRequest();
    DictProtocol.DstDict dict = putRequestDict.getDict();
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < dict.getKeysCount(); i++) {
      map.put(dict.getKeys(i), dict.getValues(i));
    }
    dstClient.dicts().put(putRequestDict.getKey(), map);
    return STATUS_OK;
  }

  public static String dictGet(DstClient dstClient, DstParsedResult parsedResult) {
    DictProtocol.GetRequest getRequestDict =
        (DictProtocol.GetRequest) parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Map<String, String> map = dstClient.dicts().get(getRequestDict.getKey());
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

  public static String dictPutItem(DstClient dstClient, DstParsedResult parsedResult) {
    DictProtocol.PutItemRequest putItemRequestDict =
        (DictProtocol.PutItemRequest) parsedResult.getRequest();
    dstClient.dicts().putItem(putItemRequestDict.getKey(),
        putItemRequestDict.getItemKey(), putItemRequestDict.getItemValue());
    return STATUS_OK;
  }

  public static String dictGetItem(DstClient dstClient, DstParsedResult parsedResult) {
    DictProtocol.GetItemRequest getItemRequestDict =
        (DictProtocol.GetItemRequest) parsedResult.getRequest();
    return dstClient.dicts().getItem(getItemRequestDict.getKey(),
        getItemRequestDict.getItemKey());
  }

  public static String dictPopItem(DstClient dstClient, DstParsedResult parsedResult) {
    DictProtocol.PopItemRequest popItemRequestDict =
        (DictProtocol.PopItemRequest) parsedResult.getRequest();
    return dstClient.dicts().popItem(popItemRequestDict.getKey(),
        popItemRequestDict.getItemKey());
  }

  public static String dictRemoveItem(DstClient dstClient, DstParsedResult parsedResult) {
    DictProtocol.RemoveItemRequest removeItemRequestDict =
        (DictProtocol.RemoveItemRequest) parsedResult.getRequest();
    dstClient.dicts().removeItem(removeItemRequestDict.getKey(),
        removeItemRequestDict.getItemKey());
    return STATUS_OK;
  }

  public static String dictDrop(DstClient dstClient, DstParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequestDict =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    dstClient.dicts().drop(dropRequestDict.getKey());
    return STATUS_OK;
  }

  public static String slistPut(DstClient dstClient, DstParsedResult parsedResult) {
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
  }

  public static String slistTop(DstClient dstClient, DstParsedResult parsedResult) {
    SortedListProtocol.TopRequest topRequestSlist =
        (SortedListProtocol.TopRequest) parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    LinkedList<SortedListEntity> listEntities = dstClient.sortedLists()
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

  public static String slistIncrScore(DstClient dstClient, DstParsedResult parsedResult) {
    SortedListProtocol.IncrScoreRequest incrScoreRequest =
        (SortedListProtocol.IncrScoreRequest) parsedResult.getRequest();
    dstClient.sortedLists().incrScore(incrScoreRequest.getKey(),
        incrScoreRequest.getMember(), incrScoreRequest.getDelta());
    return STATUS_OK;
  }

  public static String slistPutMember(DstClient dstClient, DstParsedResult parsedResult) {
    SortedListProtocol.PutMemberRequest putMemberRequest =
        (SortedListProtocol.PutMemberRequest) parsedResult.getRequest();
    final String member = putMemberRequest.getMember();
    final int score = putMemberRequest.getScore();
    dstClient.sortedLists().putMember(putMemberRequest.getKey(),
        new SortedListEntity(member, score));
    return STATUS_OK;
  }

  public static String slistRemoveMember(DstClient dstClient, DstParsedResult parsedResult) {
    SortedListProtocol.RemoveMemberRequest removeMemberRequest =
        (SortedListProtocol.RemoveMemberRequest) parsedResult.getRequest();
    dstClient.sortedLists().removeMember(removeMemberRequest.getKey(),
        removeMemberRequest.getMember());
    return STATUS_OK;
  }

  public static String slistDrop(DstClient dstClient, DstParsedResult parsedResult) {
    CommonProtocol.DropRequest dropRequest =
        (CommonProtocol.DropRequest) parsedResult.getRequest();
    dstClient.sortedLists().drop(dropRequest.getKey());
    return STATUS_OK;
  }

  public static String slistGetMember(DstClient dstClient, DstParsedResult parsedResult) {
    SortedListProtocol.GetMemberRequest getMemberRequest =
        (SortedListProtocol.GetMemberRequest) parsedResult.getRequest();
    final DstTuple<Integer, Integer> tuple = dstClient.sortedLists().getMember(
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
