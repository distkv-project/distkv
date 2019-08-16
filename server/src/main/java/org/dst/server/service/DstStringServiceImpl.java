package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.server.generated.StringProtocol;

public class DstStringServiceImpl implements DstStringService {

  private KVStore store;

  public DstStringServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public StringProtocol.StringPutResponse strPut(StringProtocol.StringPutRequest request) {
    StringProtocol.StringPutResponse.Builder responseBuilder =
            StringProtocol.StringPutResponse.newBuilder();
    store.str().put(request.getKey(), request.getValue());
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

  @Override
  public StringProtocol.StringGetResponse strGet(StringProtocol.StringGetRequest request) {
    StringProtocol.StringGetResponse.Builder responseBuilder =
            StringProtocol.StringGetResponse.newBuilder();
    responseBuilder.setValue(store.str().get(request.getKey()));
    responseBuilder.setStatus("ok");
    return responseBuilder.build();
  }

}
