package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.server.generated.DictProtocol;
import java.util.HashMap;
import java.util.Map;

public class DstDictServiceImpl implements DstDictService {

  private KVStore store;

  public DstDictServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public DictProtocol.PutResponse put(DictProtocol.PutRequest request) {
    DictProtocol.PutResponse.Builder responseBuilder =
            DictProtocol.PutResponse.newBuilder();
    String result;
    try {
      Map<String,String> map = new HashMap<>();
      DictProtocol.DstDict dstDict = request.getDict();
      for (int i = 0;i < dstDict.getKeysCount();i++) {
        map.put(dstDict.getKeys(i), dstDict.getValues(i));
      }
      store.dicts().put(request.getKey(), map);
      result = "ok";
    } catch (Exception e) {
      // TODO: Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.GetResponse get(DictProtocol.GetRequest request) {
    DictProtocol.GetResponse.Builder responseBuilder =
            DictProtocol.GetResponse.newBuilder();
    Map<String,String> values = store.dicts().get(request.getKey());
    DictProtocol.DstDict.Builder builder = DictProtocol.DstDict.newBuilder();
    for (Map.Entry<String,String> entry : values.entrySet()) {
      builder.addKeys(entry.getKey());
      builder.addValues(entry.getValue());
    }
    responseBuilder.setDict(builder.build());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.GetItemValueResponse getItemValue(DictProtocol.GetItemValueRequest request) {
    DictProtocol.GetItemValueResponse.Builder responseBuilder =
            DictProtocol.GetItemValueResponse.newBuilder();
    Map<String,String> dict = store.dicts().get(request.getKey());
    responseBuilder.setItemValue(dict.get(request.getItemKey()));
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.PopItemResponse popItem(DictProtocol.PopItemRequest request) {
    DictProtocol.PopItemResponse.Builder responseBuilder =
            DictProtocol.PopItemResponse.newBuilder();
    Map<String,String> dict = store.dicts().get(request.getKey());
    responseBuilder.setItemValue(dict.remove(request.getItemKey()));
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.SetItemResponse setItem(DictProtocol.SetItemRequest request) {
    DictProtocol.SetItemResponse.Builder responseBuilder =
            DictProtocol.SetItemResponse.newBuilder();
    Map<String,String> dict = store.dicts().get(request.getKey());
    dict.put(request.getItemKey(),request.getItemValue());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.DelResponse del(DictProtocol.DelRequest request) {
    DictProtocol.DelResponse.Builder responseBuilder =
            DictProtocol.DelResponse.newBuilder();
    store.dicts().del(request.getKey());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.DelItemResponse delItem(DictProtocol.DelItemRequest request) {
    DictProtocol.DelItemResponse.Builder responseBuilder =
            DictProtocol.DelItemResponse.newBuilder();
    Map<String,String> dict = store.dicts().get(request.getKey());
    dict.remove(request.getItemKey());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }
}
