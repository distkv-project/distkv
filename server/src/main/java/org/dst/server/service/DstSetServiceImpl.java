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
  public SetProtocol.putResponse put(SetProtocol.putRequest request) {
    SetProtocol.putResponse.Builder setPutResponseBuilder =
            SetProtocol.putResponse.newBuilder();

    store.set().put(request.getKey(), new HashSet<>(request.getValuesList()));
    setPutResponseBuilder.setStatus(CommonProtocol.Status.OK);

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.getResponse get(SetProtocol.getRequest request) {
    SetProtocol.getResponse.Builder setGetResponseBuilder =
            SetProtocol.getResponse.newBuilder();

    Set<String> values = store.set().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValues(value));

    setGetResponseBuilder.setStatus(CommonProtocol.Status.OK);
    return setGetResponseBuilder.build();
  }

  @Override
  public SetProtocol.deleteResponse delete(SetProtocol.deleteRequest request) {
    SetProtocol.deleteResponse.Builder setDeleteResponseBuilder =
            SetProtocol.deleteResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = store.set().del(request.getKey(), request.getEntity());
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
  public SetProtocol.dropByKeyResponse dropByKey(SetProtocol.dropByKeyRequest request) {
    SetProtocol.dropByKeyResponse.Builder setDropByKeyResponseBuilder =
            SetProtocol.dropByKeyResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = store.set().dropByKey(request.getKey());
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
  public SetProtocol.existsResponse exists(SetProtocol.existsRequest request) {
    SetProtocol.existsResponse.Builder setExistResponseBuilder =
            SetProtocol.existsResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = store.set().exists(request.getKey(), request.getEntity());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (Exception e) {
      //TODO(qwang): Use DstException instead of Exception here.
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }

    setExistResponseBuilder.setStatus(status);

    return setExistResponseBuilder.build();
  }

}
