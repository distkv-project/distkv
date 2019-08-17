package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.server.generated.SetProtocol;

public class DstSetServiceImpl implements DstSetService {

  private KVStore store;

  public DstSetServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public SetProtocol.SetPutResponse setPut(SetProtocol.SetPutRequest request) {
    SetProtocol.SetPutResponse.Builder setPutResponseBuilder =
            SetProtocol.SetPutResponse.newBuilder();

    store.set().put(request.getKey(), new HashSet<>(request.getValuesList()));
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

  @Override
  public SetProtocol.SetDeleteResponse setDelete(SetProtocol.SetDeleteRequest request) {
    SetProtocol.SetDeleteResponse.Builder setDeleteResponseBuilder =
            SetProtocol.SetDeleteResponse.newBuilder();

    if (store.set().del(request.getKey())) {
      setDeleteResponseBuilder.setStatus("ok");
    } else {
      setDeleteResponseBuilder.setStatus("wrong");
    }

    return setDeleteResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetExistResponse setExist(SetProtocol.SetExistRequest request) {
    SetProtocol.SetExistResponse.Builder setExistResponseBuilder =
            SetProtocol.SetExistResponse.newBuilder();

    String status;
    try {
      store.set().exists(request.getKey(), request.getValue());
      status = "ok";
    } catch (Exception e) {
      status = e.getMessage();
    }

    setExistResponseBuilder.setStatus(status);

    return setExistResponseBuilder.build();
  }

}
