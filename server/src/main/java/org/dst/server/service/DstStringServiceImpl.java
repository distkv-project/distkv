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
  public StringProtocol.PutResponse put(StringProtocol.PutRequest request) {
    StringProtocol.PutResponse.Builder responseBuilder =
            StringProtocol.PutResponse.newBuilder();
    store.strs().put(request.getKey(), request.getValue());
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    return responseBuilder.build();
  }

  @Override
  public StringProtocol.GetResponse get(StringProtocol.GetRequest request) {
    StringProtocol.GetResponse.Builder responseBuilder =
            StringProtocol.GetResponse.newBuilder();

    String value = store.strs().get(request.getKey());
    if (value != null) {
      responseBuilder.setValue(store.strs().get(request.getKey()));
      responseBuilder.setStatus(CommonProtocol.Status.OK);
    } else {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
    return responseBuilder.build();
  }

}
