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
  public DictProtocol.DictPutResponse put(DictProtocol.DictPutRequest request) {
    DictProtocol.DictPutResponse.Builder responseBuilder =
            DictProtocol.DictPutResponse.newBuilder();
    String result;
    try {
      Map<String,String> map = new HashMap<>();
      DictProtocol.DstDict dstDict = request.getValues();
      for (int i = 0;i < dstDict.getKeysCount();i++) {
        map.put(dstDict.getKeys(i), dstDict.getDict(i));
      }
      store.dict().put(request.getKey(), map);
      result = "ok";
    } catch (Exception e) {
      // TODO: Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public DictProtocol.DictGetResponse get(DictProtocol.DictGetRequest request) {
    DictProtocol.DictGetResponse.Builder responseBuilder =
            DictProtocol.DictGetResponse.newBuilder();
    Map<String,String> values = store.dict().get(request.getKey());
    DictProtocol.DstDict.Builder builder = DictProtocol.DstDict.newBuilder();
    for (Map.Entry<String,String> entry : values.entrySet()) {
      builder.addKeys(entry.getKey());
      builder.addDict(entry.getValue());
    }
    responseBuilder.setDict(builder.build());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }
}
