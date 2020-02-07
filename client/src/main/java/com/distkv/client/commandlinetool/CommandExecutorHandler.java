package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DictProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberResponse;
import com.distkv.rpc.protobuf.generated.StringProtocol.StrPutRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandExecutorHandler {

  private static final String STATUS_OK = "ok";

  public static String strPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
      DistkvRequest request =parsedResult.getRequest();
      distkvClient.strs().put(request);
    return STATUS_OK;
  }

  public static String strGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    return distkvClient.strs().get(request);
  }

  public static String strDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.strs().drop(request);
    return STATUS_OK;
  }

  public static String listPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.lists().put(request);
    return STATUS_OK;
  }

  public static String listGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    return distkvClient.lists().get(request).toString();
  }

  public static String listLPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.lists().lput(request);
    return STATUS_OK;
  }

  public static String listRPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.lists().rput(request);
    return STATUS_OK;
  }

  public static String listRemove(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
      distkvClient.lists().remove(request);
      return STATUS_OK;
  }

  public static String listMRemove(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.lists().mremove(request);
    return STATUS_OK;
  }

  public static String listDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.lists().drop(request);
    return STATUS_OK;
  }

  public static String setPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sets().put(request);
    return STATUS_OK;
  }

  public static String setGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Set<String> set = distkvClient.sets().get(request);
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
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sets().drop(request);
    return STATUS_OK;
  }

  public static String setPutItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sets().putItem(request);
    return STATUS_OK;
  }

  public static String setRemoveItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sets().removeItem(request);
    return STATUS_OK;
  }

  public static String setExists(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    return String.valueOf(distkvClient.sets().exists(request));
  }

  public static String dictPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.dicts().put(request);
    return STATUS_OK;
  }

  public static String dictGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Map<String, String> map = distkvClient.dicts().get(request);
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
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.dicts().putItem(request);
    return STATUS_OK;
  }

  public static String dictGetItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    return distkvClient.dicts().getItem(request);
  }

  public static String dictPopItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    return distkvClient.dicts().popItem(request);
  }

  public static String dictRemoveItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.dicts().removeItem(request);
    return STATUS_OK;
  }

  public static String dictDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.dicts().drop(request);
    return STATUS_OK;
  }

  public static String slistPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sortedLists().put(request);
    return STATUS_OK;
  }

  public static String slistTop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    LinkedList<SortedListEntity> listEntities = distkvClient.sortedLists().top(request);
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
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sortedLists().incrScore(request);
    return STATUS_OK;
  }

  public static String slistPutMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sortedLists().putMember(request);
    return STATUS_OK;
  }

  public static String slistRemoveMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sortedLists().removeMember(request);
    return STATUS_OK;
  }

  public static String slistDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    distkvClient.sortedLists().drop(request);
    return STATUS_OK;
  }

  public static String slistGetMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request =parsedResult.getRequest();
    SlistGetMemberResponse response ;
    try {
      response=request.getRequest().unpack(SlistGetMemberResponse.class);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    final DistKVTuple<Integer, Integer> tuple = distkvClient.sortedLists().getMember(request);
    // output: (member, score), ranking
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    stringBuilder.append(response.getEntity().getMember());
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
