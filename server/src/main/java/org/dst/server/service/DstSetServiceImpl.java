package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.protocol.StatusProtocol;
import org.dst.protocol.SetProtocol;

public class DstSetServiceImpl implements DstSetService {

  private KVStore store;

  public DstSetServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public SetProtocol.SetPutResponse setPut(SetProtocol.SetPutRequest request) {
    SetProtocol.SetPutResponse.Builder setPutResponseBuilder =
            SetProtocol.SetPutResponse.newBuilder();

    store.sets().put(request.getKey(), new HashSet<>(request.getValuesList()));
    setPutResponseBuilder.setStatus(StatusProtocol.Status.OK);

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetGetResponse setGet(SetProtocol.SetGetRequest request) {
    SetProtocol.SetGetResponse.Builder setGetResponseBuilder =
            SetProtocol.SetGetResponse.newBuilder();

    Set<String> values = store.sets().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValues(value));

    setGetResponseBuilder.setStatus(StatusProtocol.Status.OK);
    return setGetResponseBuilder.build();
  }
}
