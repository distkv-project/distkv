package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.server.base.DstBaseService;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.DictProtocol;
import java.util.HashMap;
import java.util.Map;

public class DstDictServiceImpl extends DstBaseService implements DstDictService  {

  public DstDictServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public DictProtocol.PutResponse put(DictProtocol.PutRequest request) {
    DictProtocol.PutResponse.Builder responseBuilder =
            DictProtocol.PutResponse.newBuilder();
    try {
      final Map<String,String> map = new HashMap<>();
      DictProtocol.DstDict dstDict = request.getDict();
      for (int i = 0;i < dstDict.getKeysCount();i++) {
        map.put(dstDict.getKeys(i), dstDict.getValues(i));
      }
      getStore().dicts().put(request.getKey(), map);
      responseBuilder.setStatus(CommonProtocol.Status.OK);
    } catch (Exception e) {
      // TODO: Use DstException instead of Exception here.
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }

    return responseBuilder.build();
  }

  @Override
  public DictProtocol.GetResponse get(DictProtocol.GetRequest request) {
    DictProtocol.GetResponse.Builder responseBuilder =
            DictProtocol.GetResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String,String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    DictProtocol.DstDict.Builder builder = DictProtocol.DstDict.newBuilder();
    for (Map.Entry<String,String> entry : dict.entrySet()) {
      builder.addKeys(entry.getKey());
      builder.addValues(entry.getValue());
    }
    responseBuilder.setDict(builder.build());
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.GetItemValueResponse getItemValue(DictProtocol.GetItemValueRequest request) {
    DictProtocol.GetItemValueResponse.Builder responseBuilder =
            DictProtocol.GetItemValueResponse.newBuilder();
    final Map<String,String> dict = getStore().dicts().get(request.getKey());
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    final String itemValue = dict.get(request.getItemKey());
    if (itemValue == null) {
      responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    responseBuilder.setItemValue(itemValue);
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.PopItemResponse popItem(DictProtocol.PopItemRequest request) {
    DictProtocol.PopItemResponse.Builder responseBuilder =
            DictProtocol.PopItemResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String,String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    final String itemValue = dict.remove(request.getItemKey());
    if (itemValue == null) {
      responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      return  responseBuilder.build();
    } else {
      responseBuilder.setItemValue(itemValue);
    }
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.PutItemResponse putItem(DictProtocol.PutItemRequest request) {
    DictProtocol.PutItemResponse.Builder responseBuilder =
            DictProtocol.PutItemResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String,String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return  responseBuilder.build();
    }
    dict.put(request.getItemKey(),request.getItemValue());
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.DelResponse del(DictProtocol.DelRequest request) {
    DictProtocol.DelResponse.Builder responseBuilder =
            DictProtocol.DelResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    if (!getStore().dicts().del(request.getKey())) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.DelItemResponse delItem(DictProtocol.DelItemRequest request) {
    DictProtocol.DelItemResponse.Builder responseBuilder =
            DictProtocol.DelItemResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String,String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    final String itemValue = dict.remove(request.getItemKey());
    if (itemValue == null) {
      responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    dict.remove(request.getItemKey());
    return responseBuilder.build();
  }
}
