package com.distkv.client.commandlinetool;

import com.distkv.client.DistkvClient;
import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.timeunit.TimeUnit;
import com.distkv.parser.po.DistkvParsedResult;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictGetItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPopItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictPutRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DictRemoveItemRequest;
import com.distkv.rpc.protobuf.generated.DictProtocol.DistKVDict;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.distkv.rpc.protobuf.generated.IntProtocol.IntIncrRequest;
import com.distkv.rpc.protobuf.generated.IntProtocol.IntPutRequest;
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
import com.distkv.rpc.protobuf.generated.SlistProtocol;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistGetMemberRequest;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistIncrScoreRequest;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistPutMemberRequest;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistPutRequest;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistRemoveMemberRequest;
import com.distkv.rpc.protobuf.generated.SlistProtocol.SlistTopRequest;
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

  public static String slistPut(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistPutRequest slistPutRequest = request.getRequest().unpack(SlistPutRequest.class);
      final LinkedList<SlistEntity> sortedListEntitiesResult = new LinkedList<>();
      final List<SlistProtocol.SlistEntity> sortedListEntities
          = slistPutRequest.getListList();
      for (SlistProtocol.SlistEntity sortedListEntity : sortedListEntities) {
        final String sortedListEntityMember = sortedListEntity.getMember();
        final int sortedListEntityScore = sortedListEntity.getScore();
        sortedListEntitiesResult.add(new SlistEntity(sortedListEntityMember,
            sortedListEntityScore));
      }
      distkvClient.slists().put(request.getKey(), sortedListEntitiesResult);
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
      LinkedList<SlistEntity> listEntities = distkvClient.slists()
          .top(request.getKey(), slistTopRequest.getCount());
      boolean first = true;
      stringBuilder.append("[");
      for (final SlistEntity entity : listEntities) {
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
      distkvClient.slists().incrScore(
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
      distkvClient.slists().putMember(request.getKey(), new SlistEntity(member, score));
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
      distkvClient.slists().removeMember(
          request.getKey(), slistRemoveMemberRequest.getMember());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }

    return STATUS_OK;
  }

  public static String slistGetMember(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      SlistGetMemberRequest slistGetMemberRequest =
          request.getRequest().unpack(SlistGetMemberRequest.class);
      final DistkvTuple<Integer, Integer> tuple =
          distkvClient.slists().getMember(request.getKey(), slistGetMemberRequest.getMember());
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

  public static String activeNamespace(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.activeNamespace(request.getNamespace());
    return STATUS_OK;
  }

  public static String deactiveNamespace(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    if (distkvClient.getActivedNamespace() == null) {
      return "Namespace has not been activated";
    }
    distkvClient.deactiveNamespace();
    return STATUS_OK;
  }

  public static String intPut(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      IntPutRequest intPutRequest = request.getRequest().unpack(IntPutRequest.class);
      distkvClient.ints().put(request.getKey(), intPutRequest.getValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String intGet(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      return String.valueOf(distkvClient.ints().get(request.getKey()));
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public static String intIncr(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      IntIncrRequest intIncrRequest = request.getRequest().unpack(IntIncrRequest.class);
      distkvClient.ints().incr(request.getKey(), intIncrRequest.getDelta());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String expire(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    try {
      DistkvRequest request = parsedResult.getRequest();
      ExpireRequest expireRequest = request.getRequest().unpack(ExpireRequest.class);
      distkvClient.expire(request.getKey(), expireRequest.getExpireTime());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
    return STATUS_OK;
  }

  public static String drop(
      DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    distkvClient.drop(request.getKey());
    return STATUS_OK;
  }

  public static String ttl(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    return distkvClient.ttl(request.getKey()) + TimeUnit.MILLISECOND;
  }

  public static String exists(DistkvClient distkvClient, DistkvParsedResult parsedResult) {
    DistkvRequest request = parsedResult.getRequest();
    return String.valueOf(distkvClient.exists(request.getKey()));
  }
}
