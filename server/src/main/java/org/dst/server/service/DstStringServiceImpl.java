package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.server.generated.DstServerProtocol;

public class DstStringServiceImpl implements DstStringService {

  private KVStore store;

  public DstStringServiceImpl() {
    store = new KVStoreImpl();
  }

  @Override
  public DstServerProtocol.StringPutResponse strPut(DstServerProtocol.StringPutRequest request) {
    DstServerProtocol.StringPutResponse.Builder responseBuilder = DstServerProtocol.StringPutResponse.newBuilder();
      store.str().put(request.getKey(), request.getValue());
      responseBuilder.setResult("ok");
    return responseBuilder.build();
  }

  @Override
  public DstServerProtocol.StringGetResponse strGet(DstServerProtocol.StringGetRequest request) {
    DstServerProtocol.StringGetResponse.Builder responseBuilder = DstServerProtocol.StringGetResponse.newBuilder();
    responseBuilder.setResult(store.str().get(request.getKey()));
    return responseBuilder.build();
  }
}
