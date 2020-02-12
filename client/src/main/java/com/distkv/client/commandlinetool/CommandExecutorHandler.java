package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictRemoveItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DistKVDict;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListLPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListMRemoveRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListRPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListRemoveRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetPutItemRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetPutRequest;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetRemoveItemRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistGetMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistIncrScoreRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistPutRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistRemoveMemberRequest;
import com.distkv.rpc.protobuf.generated.SortedListProtocol.SlistTopRequest;
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
    try {
      DistkvRequest request = parsedResult.getRequest();
      StrPutRequest strPutRequest = request.getRequest().unpack(StrPutRequest.class);
      distkvClient.strs().put(request.getKey(), strPutRequest.getValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String strGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      return distkvClient.strs().get(request.getKey());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public static String strDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.strs().drop(request.getKey());
    return STATUS_OK;
  }

  public static String listPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ListPutRequest listPutRequest = request.getRequest().unpack(ListPutRequest.class);
      distkvClient.lists().put(request.getKey(), listPutRequest.getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String listGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ListGetRequest listGetRequest = request.getRequest().unpack(ListGetRequest.class);
      List<String> list = null;
      if (listGetRequest.getType() == ListProtocol.GetType.GET_ALL) {
        list = distkvClient.lists().get(request.getKey());
      } else if (listGetRequest.getType() == ListProtocol.GetType.GET_ONE) {
        list = distkvClient.lists().get(request.getKey(), listGetRequest.getIndex());
      } else if (listGetRequest.getType() == ListProtocol.GetType.GET_RANGE) {
        list = distkvClient.lists().get(request.getKey(),
            listGetRequest.getFrom(), listGetRequest.getEnd());
      }
      return list == null ? null : list.toString();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public static String listLPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ListLPutRequest listLPutRequest = request.getRequest().unpack(ListLPutRequest.class);
      distkvClient.lists().lput(request.getKey(), listLPutRequest.getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String listRPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ListRPutRequest listRPutRequest = request.getRequest().unpack(ListRPutRequest.class);
      distkvClient.lists().rput(request.getKey(), listRPutRequest.getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

    return STATUS_OK;
  }

  public static String listRemove(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ListRemoveRequest listRemoveRequest = request.getRequest().unpack(ListRemoveRequest.class);
      if (listRemoveRequest.getType() == ListProtocol.RemoveType.RemoveOne) {
        distkvClient.lists().remove(request.getKey(), listRemoveRequest.getIndex());
      } else if (listRemoveRequest.getType() == ListProtocol.RemoveType.RemoveRange) {
        distkvClient.lists().remove(request.getKey(),
            listRemoveRequest.getFrom(), listRemoveRequest.getEnd());
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String listMRemove(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ListMRemoveRequest listMRemoveRequest = request.getRequest().unpack(ListMRemoveRequest.class);
      distkvClient.lists().mremove(request.getKey(), listMRemoveRequest.getIndexesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String listDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.lists().drop(request.getKey());
    return STATUS_OK;
  }

  public static String setPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SetPutRequest setPutRequest = request.getRequest().unpack(SetPutRequest.class);
      distkvClient.sets().put(request.getKey(), new HashSet<>(setPutRequest.getValuesList()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

    return STATUS_OK;
  }

  public static String setGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Set<String> set = distkvClient.sets().get(request.getKey());
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
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.sets().drop(request.getKey());
    return STATUS_OK;
  }

  public static String setPutItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SetPutItemRequest setPutItemRequest = request.getRequest().unpack(SetPutItemRequest.class);
      distkvClient.sets().putItem(request.getKey(), setPutItemRequest.getItemValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String setRemoveItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SetRemoveItemRequest setRemoveItemRequest = request.getRequest()
          .unpack(SetRemoveItemRequest.class);
      distkvClient.sets().removeItem(request.getKey(), setRemoveItemRequest.getItemValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String setExists(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SetExistsRequest setExistsRequest = request.getRequest().unpack(SetExistsRequest.class);
      return String
          .valueOf(distkvClient.sets().exists(request.getKey(), setExistsRequest.getEntity()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public static String dictPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      DictPutRequest dictPutRequest = request.getRequest().unpack(DictPutRequest.class);
      DistKVDict dict = dictPutRequest.getDict();
      Map<String, String> map = new HashMap<>();
      for (int i = 0; i < dict.getKeysCount(); i++) {
        map.put(dict.getKeys(i), dict.getValues(i));
      }
      distkvClient.dicts().put(request.getKey(), map);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String dictGet(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    Map<String, String> map = distkvClient.dicts().get(request.getKey());
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
    try {
      DistkvRequest request = parsedResult.getRequest();
      DictPutItemRequest dictPutItemRequest = request.getRequest().unpack(DictPutItemRequest.class);
      distkvClient.dicts().putItem(
          request.getKey(), dictPutItemRequest.getItemKey(), dictPutItemRequest.getItemValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String dictGetItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      DictGetItemRequest dictGetItemRequest = request.getRequest().unpack(DictGetItemRequest.class);
      return distkvClient.dicts().getItem(request.getKey(), dictGetItemRequest.getItemKey());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public static String dictPopItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      DictPopItemRequest dictPopItemRequest = request.getRequest().unpack(DictPopItemRequest.class);
      return distkvClient.dicts().popItem(request.getKey(), dictPopItemRequest.getItemKey());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public static String dictRemoveItem(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      DictRemoveItemRequest dictRemoveItemRequest =
          request.getRequest().unpack(DictRemoveItemRequest.class);
      distkvClient.dicts().removeItem(request.getKey(), dictRemoveItemRequest.getItemKey());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String dictDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.dicts().drop(request.getKey());
    return STATUS_OK;
  }

  public static String slistPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistPutRequest slistPutRequest = request.getRequest().unpack(SlistPutRequest.class);
      final LinkedList<SortedListEntity> sortedListEntitiesResult = new LinkedList<>();
      final List<SortedListProtocol.SortedListEntity> sortedListEntities
          = slistPutRequest.getListList();
      for (SortedListProtocol.SortedListEntity sortedListEntity : sortedListEntities) {
        final String sortedListEntityMember = sortedListEntity.getMember();
        final int sortedListEntityScore = sortedListEntity.getScore();
        sortedListEntitiesResult.add(new SortedListEntity(sortedListEntityMember,
            sortedListEntityScore));
      }
      distkvClient.sortedLists().put(request.getKey(), sortedListEntitiesResult);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String slistTop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistTopRequest slistTopRequest = request.getRequest().unpack(SlistTopRequest.class);
      final StringBuilder stringBuilder = new StringBuilder();
      LinkedList<SortedListEntity> listEntities = distkvClient.sortedLists()
          .top(request.getKey(), slistTopRequest.getCount());
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
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

  }

  public static String slistIncrScore(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistIncrScoreRequest slistIncrScoreRequest = request.getRequest()
          .unpack(SlistIncrScoreRequest.class);
      distkvClient.sortedLists().incrScore(
          request.getKey(), slistIncrScoreRequest.getMember(), slistIncrScoreRequest.getDelta());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String slistPutMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistPutMemberRequest slistPutMemberRequest =
          request.getRequest().unpack(SlistPutMemberRequest.class);
      final String member = slistPutMemberRequest.getMember();
      final int score = slistPutMemberRequest.getScore();
      distkvClient.sortedLists().putMember(request.getKey(), new SortedListEntity(member, score));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

    return STATUS_OK;
  }

  public static String slistRemoveMember(DistkvClient distkvClient,
      DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistRemoveMemberRequest slistRemoveMemberRequest =
          request.getRequest().unpack(SlistRemoveMemberRequest.class);
      distkvClient.sortedLists().removeMember(
          request.getKey(), slistRemoveMemberRequest.getMember());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

    return STATUS_OK;
  }

  public static String slistDrop(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.sortedLists().drop(request.getKey());
    return STATUS_OK;
  }

  public static String slistGetMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistGetMemberRequest slistGetMemberRequest =
          request.getRequest().unpack(SlistGetMemberRequest.class);
      final DistkvTuple<Integer, Integer> tuple =
          distkvClient.sortedLists().getMember(request.getKey(), slistGetMemberRequest.getMember());
      // output: (member, score), ranking
      final StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("(");
      stringBuilder.append(slistGetMemberRequest.getMember());
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
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

  }
}
