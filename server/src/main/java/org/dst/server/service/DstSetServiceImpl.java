package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.SetProtocol;
import org.dst.utils.Status;

public class DstSetServiceImpl implements DstSetService {

  private KVStore store;

  public DstSetServiceImpl(KVStore kvStore) {
    store = kvStore;
  }

  @Override
  public SetProtocol.PutResponse put(SetProtocol.PutRequest request) {
    SetProtocol.PutResponse.Builder setPutResponseBuilder =
            SetProtocol.PutResponse.newBuilder();

    store.sets().put(request.getKey(), new HashSet<>(request.getValuesList()));
    setPutResponseBuilder.setStatus(CommonProtocol.Status.OK);

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.GetResponse get(SetProtocol.GetRequest request) {
    SetProtocol.GetResponse.Builder setGetResponseBuilder =
            SetProtocol.GetResponse.newBuilder();

    Set<String> values = store.sets().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValues(value));

    setGetResponseBuilder.setStatus(CommonProtocol.Status.OK);
    return setGetResponseBuilder.build();
  }

  @Override
  public SetProtocol.DeleteResponse delete(SetProtocol.DeleteRequest request) {
    SetProtocol.DeleteResponse.Builder setDeleteResponseBuilder =
            SetProtocol.DeleteResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = store.sets().del(request.getKey(), request.getEntity());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (Exception e) {
      //TODO(qwang): Use DstException instead of Exception here.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }

    setDeleteResponseBuilder.setStatus(status);

    return setDeleteResponseBuilder.build();
  }

  @Override
  public SetProtocol.DropByKeyResponse dropByKey(SetProtocol.DropByKeyRequest request) {
    SetProtocol.DropByKeyResponse.Builder setDropByKeyResponseBuilder =
            SetProtocol.DropByKeyResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = store.sets().dropByKey(request.getKey());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (Exception e) {
      //TODO(qwang): Use DstException instead of Exception here.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }

    setDropByKeyResponseBuilder.setStatus(status);

    return setDropByKeyResponseBuilder.build();
  }

  @Override
  public SetProtocol.ExistsResponse exists(SetProtocol.ExistsRequest request) {
    SetProtocol.ExistsResponse.Builder setExistResponseBuilder =
            SetProtocol.ExistsResponse.newBuilder();

    boolean result;
    try {
      store.sets().exists(request.getKey(), request.getEntity());
      result = true;
    } catch (Exception e) {
      //TODO(qwang): Use DstException instead of Exception here.
      result = false;
    }

    setExistResponseBuilder.setResult(result);

    return setExistResponseBuilder.build();
  }

}
