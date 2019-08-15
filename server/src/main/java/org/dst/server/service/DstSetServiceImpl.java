package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.server.generated.DstServerSetProtocol;

public class DstSetServiceImpl implements DstSetService {

  private KVStore store;

  public DstSetServiceImpl() {
    store = new KVStoreImpl();
  }

  @Override
  public DstServerSetProtocol.SetPutResponse setPut(DstServerSetProtocol.SetPutRequest request) {
    DstServerSetProtocol.SetPutResponse.Builder setPutResponseBuilder =
            DstServerSetProtocol.SetPutResponse.newBuilder();

    store.set().put(request.getKey(), new HashSet<>(request.getValueList()));
    setPutResponseBuilder.setStatus("ok");

    return setPutResponseBuilder.build();
  }

  @Override
  public DstServerSetProtocol.SetGetResponse setGet(DstServerSetProtocol.SetGetRequest request) {
    DstServerSetProtocol.SetGetResponse.Builder setGetResponseBuilder =
            DstServerSetProtocol.SetGetResponse.newBuilder();

    Set<String> values = store.set().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValue(value));

    setGetResponseBuilder.setStatus("ok");
    return setGetResponseBuilder.build();
  }
}
