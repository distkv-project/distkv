package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.server.generated.SetProtocol;

public class DstSetServiceImpl implements DstSetService {

  private KVStore store;

  public DstSetServiceImpl() {
    store = new KVStoreImpl();
  }

  @Override
  public SetProtocol.SetPutResponse setPut(SetProtocol.SetPutRequest request) {
    SetProtocol.SetPutResponse.Builder setPutResponseBuilder =
            SetProtocol.SetPutResponse.newBuilder();

    store.set().put(request.getKey(), new HashSet<>(request.getValueList()));
    setPutResponseBuilder.setStatus("ok");

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetGetResponse setGet(SetProtocol.SetGetRequest request) {
    SetProtocol.SetGetResponse.Builder setGetResponseBuilder =
            SetProtocol.SetGetResponse.newBuilder();

    Set<String> values = store.set().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValues(value));

    setGetResponseBuilder.setStatus("ok");
    return setGetResponseBuilder.build();
  }
}
