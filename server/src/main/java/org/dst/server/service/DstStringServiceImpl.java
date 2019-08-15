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
    responseBuilder.setResponse("ok");
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.StringGetResponse strGet(DstServerProtocol.StringGetRequest request) {
    DstServerProtocol.StringGetResponse.Builder responseBuilder =
            DstServerProtocol.StringGetResponse.newBuilder();
    responseBuilder.setValue(store.str().get(request.getKey()));
    responseBuilder.setResponse("ok");
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.ListPutResponse listPut(DstServerProtocol.ListPutRequest request) {
    DstServerProtocol.ListPutResponse.Builder responseBuilder =
            DstServerProtocol.ListPutResponse.newBuilder();
    String result;
    try {
      store.list().put(request.getKey(), request.getValueList());
      result = "ok";
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      result = e.getMessage();
    }
    responseBuilder.setResponse(result);
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.ListGetResponse listGet(DstServerProtocol.ListGetRequest request) {
    DstServerProtocol.ListGetResponse.Builder responseBuilder =
            DstServerProtocol.ListGetResponse.newBuilder();

    List<String> values = store.list().get(request.getKey());
    values.forEach(value -> responseBuilder.addValue(value));

    responseBuilder.setResponse("ok");
    return responseBuilder.build();
  }
}
