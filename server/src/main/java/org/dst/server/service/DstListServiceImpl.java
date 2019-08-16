package org.dst.server.service;

import java.util.List;
import org.dst.core.KVStore;
import org.dst.server.generated.ListProtocol;

public class DstListServiceImpl implements DstListService {

  private KVStore store;

  public DstListServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public ListProtocol.ListPutResponse listPut(ListProtocol.ListPutRequest request) {
    ListProtocol.ListPutResponse.Builder responseBuilder =
            ListProtocol.ListPutResponse.newBuilder();
    String result;
    try {
      store.list().put(request.getKey(), request.getValuesList());
      result = "ok";
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setStatus(result);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.ListGetResponse listGet(ListProtocol.ListGetRequest request) {
    ListProtocol.ListGetResponse.Builder responseBuilder =
            ListProtocol.ListGetResponse.newBuilder();

    List<String> values = store.list().get(request.getKey());
    values.forEach(value -> responseBuilder.addValues(value));

    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }
}
