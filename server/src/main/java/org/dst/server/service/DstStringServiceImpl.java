package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.server.generated.DstServerProtocol;

import java.util.List;

public class DstStringServiceImpl implements DstStringService {

  private KVStore store;

  public DstStringServiceImpl() {
    store = new KVStoreImpl();
  }

  @Override
  public DstServerProtocol.StringPutResponse strPut(DstServerProtocol.StringPutRequest request) {
    DstServerProtocol.StringPutResponse.Builder responseBuilder =
            DstServerProtocol.StringPutResponse.newBuilder();
    store.str().put(request.getKey(), request.getValue());
    responseBuilder.setResult("ok");
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.StringGetResponse strGet(DstServerProtocol.StringGetRequest request) {
    DstServerProtocol.StringGetResponse.Builder responseBuilder =
            DstServerProtocol.StringGetResponse.newBuilder();
    responseBuilder.setResult(store.str().get(request.getKey()));
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.ListPutResponse listPut(DstServerProtocol.ListPutRequest request) {
    DstServerProtocol.ListPutResponse.Builder responseBuilder =
            DstServerProtocol.ListPutResponse.newBuilder();
    String result;
    try {
      store.list().put(request.getKey(), request.getValuesList());
      result = "ok";
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setResult(result);
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.ListGetResponse listGet(DstServerProtocol.ListGetRequest request) {
    DstServerProtocol.ListGetResponse.Builder responseBuilder =
            DstServerProtocol.ListGetResponse.newBuilder();

    List<String> values = store.list().get(request.getKey());
    // TODO(qwang): Use for each.
    for (int i = 0; i < values.size(); ++i) {
      responseBuilder.addValues(values.get(i));
    }

    responseBuilder.setResult("ok");
    return responseBuilder.build();
  }
}
