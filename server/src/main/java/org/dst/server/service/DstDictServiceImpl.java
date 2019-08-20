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
}
