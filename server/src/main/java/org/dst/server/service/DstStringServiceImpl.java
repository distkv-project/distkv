package org.dst.server.service;

import org.dst.core.KVStore;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstStringServiceImpl implements DstStringService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstStringServiceImpl.class);

  private KVStore store;

  public DstStringServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public StringProtocol.StringPutResponse strPut(StringProtocol.StringPutRequest request) {
    StringProtocol.StringPutResponse.Builder responseBuilder =
            StringProtocol.StringPutResponse.newBuilder();
    store.str().put(request.getKey(), request.getValue());
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    return responseBuilder.build();
  }

  @Override
  public StringProtocol.StringGetResponse strGet(StringProtocol.StringGetRequest request) {
    StringProtocol.StringGetResponse.Builder responseBuilder =
            StringProtocol.StringGetResponse.newBuilder();

    String value = store.str().get(request.getKey());
    if (value != null) {
      responseBuilder.setValue(store.str().get(request.getKey()));
      responseBuilder.setStatus(CommonProtocol.Status.OK);
    } else {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
    return responseBuilder.build();
  }

}
