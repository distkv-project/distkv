package org.dst.client.commandlinetool;

import org.dst.client.DstClient;
import org.dst.parser.po.DstParsedResult;
import org.dst.rpc.protobuf.generated.DictProtocol;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.dst.rpc.protobuf.generated.ListProtocol;
import org.dst.rpc.protobuf.generated.StringProtocol;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DstCommandExecutor {

  private static final String STATUS_OK = "ok";

  DstClient dstClient;

  public DstCommandExecutor(DstClient dstClient) {
    this.dstClient = dstClient;
  }

  public String execute(DstParsedResult parsedResult) {
    switch (parsedResult.getRequestType()) {
      case STR_PUT:
        StringProtocol.PutRequest putRequestStr =
                (StringProtocol.PutRequest) parsedResult.getRequest();
        dstClient.strs().put(putRequestStr.getKey(), putRequestStr.getValue());
        return STATUS_OK;
      case STR_GET:
        StringProtocol.GetRequest getRequestStr =
                (StringProtocol.GetRequest) parsedResult.getRequest();
        return dstClient.strs().get(getRequestStr.getKey());
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
        if (dstClient.sets().exists(existsRequestSet.getKey(), existsRequestSet.getEntity())) {
          return "true";
        } else {
          return "false";
        }
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
      default:
        return null;
    }
    return null;
  }
}
