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
  public SetProtocol.SetPutResponse put(SetProtocol.SetPutRequest request) {
    SetProtocol.SetPutResponse.Builder setPutResponseBuilder =
            SetProtocol.SetPutResponse.newBuilder();

    store.set().put(request.getKey(), new HashSet<>(request.getValuesList()));
    setPutResponseBuilder.setStatus("ok");

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetGetResponse get(SetProtocol.SetGetRequest request) {
    SetProtocol.SetGetResponse.Builder setGetResponseBuilder =
            SetProtocol.SetGetResponse.newBuilder();

    Set<String> values = store.set().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValues(value));

    setGetResponseBuilder.setStatus("ok");
    return setGetResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetDeleteResponse delete(SetProtocol.SetDeleteRequest request) {
    SetProtocol.SetDeleteResponse.Builder setDeleteResponseBuilder =
            SetProtocol.SetDeleteResponse.newBuilder();

    if (store.set().del(request.getKey(), request.getEntity())) {
      setDeleteResponseBuilder.setStatus("ok");
    } else {
      setDeleteResponseBuilder.setStatus("wrong");
    }

    return setDeleteResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetDropByKeyResponse dropByKey(SetProtocol.SetDropByKeyRequest request) {
    SetProtocol.SetDropByKeyResponse.Builder setDropByKeyResponseBuilder =
            SetProtocol.SetDropByKeyResponse.newBuilder();

    if (store.set().dropByKey(request.getKey())) {
      setDropByKeyResponseBuilder.setStatus("ok");
    } else {
      setDropByKeyResponseBuilder.setStatus("wrong");
    }

    return setDropByKeyResponseBuilder.build();
  }

  @Override
  public SetProtocol.SetExistResponse exist(SetProtocol.SetExistRequest request) {
    SetProtocol.SetExistResponse.Builder setExistResponseBuilder =
            SetProtocol.SetExistResponse.newBuilder();

    String status;
    try {
      store.set().exists(request.getKey(), request.getEntity());
      status = "ok";
    } catch (Exception e) {
      status = e.getMessage();
    }

    setExistResponseBuilder.setStatus(status);

    return setExistResponseBuilder.build();
  }

}
