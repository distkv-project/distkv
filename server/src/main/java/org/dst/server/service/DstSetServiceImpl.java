package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.server.generated.DstServerProtocol;

public class DstSetServiceImpl implements DstSetService {

  private KVStore store;

  public DstSetServiceImpl() {
    store = new KVStoreImpl();
  }

  @Override
  public DstServerProtocol.SetPutResponse setPut(DstServerProtocol.SetPutRequest request) {
    DstServerProtocol.SetPutResponse.Builder setPutResponseBuilder =
            DstServerProtocol.SetPutResponse.newBuilder();

    store.set().put(request.getKey(), new HashSet<>(request.getValueList()));
    setPutResponseBuilder.setResponse("ok");

    return setPutResponseBuilder.build();
  }

  @Override
  public DstServerProtocol.SetGetResponse setGet(DstServerProtocol.SetGetRequest request) {
    DstServerProtocol.SetGetResponse.Builder setGetResponseBuilder =
            DstServerProtocol.SetGetResponse.newBuilder();

    Set<String> values = store.set().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValue(value));

    setGetResponseBuilder.setResponse("ok");
    return setGetResponseBuilder.build();
  }
}
