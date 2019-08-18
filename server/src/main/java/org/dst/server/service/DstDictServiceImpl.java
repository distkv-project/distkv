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
  public DictProtocol.DictPutResponse dictPut(DictProtocol.DictPutRequest request) {
    DictProtocol.DictPutResponse.Builder responseBuilder =
            DictProtocol.DictPutResponse.newBuilder();
    String result;
    try {
      Map<String,String> map = new HashMap<>();
      DictProtocol.DstMap dstMap = request.getValues();
      for (int i = 0;i < dstMap.getKeysCount();i++) {
        map.put(dstMap.getKeys(i),dstMap.getValues(i));
      }
      store.dict().put(request.getKey(),map);
      result = "ok";
    } catch (Exception e) {
      // TODO: Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.DictGetResponse dictGet(DictProtocol.DictGetRequest request) {
    DictProtocol.DictGetResponse.Builder responseBuilder =
            DictProtocol.DictGetResponse.newBuilder();
    Map<String,String> values = store.dict().get(request.getKey());
    DictProtocol.DstMap.Builder builder = DictProtocol.DstMap.newBuilder();
    for (Map.Entry<String,String> entry : values.entrySet()) {
      builder.addKeys(entry.getKey());
      builder.addValues(entry.getValue());
    }
    responseBuilder.setValues(builder.build());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }
}
