package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.dst.rpc.service.DstSetService;
import org.dst.server.base.DstBaseService;
import org.dst.common.utils.Status;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class DstSetServiceImpl extends DstBaseService implements DstSetService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstSetServiceImpl.class);

  public DstSetServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public SetProtocol.PutResponse put(SetProtocol.PutRequest request) {
    SetProtocol.PutResponse.Builder setPutResponseBuilder =
            SetProtocol.PutResponse.newBuilder();

    getStore().sets().put(request.getKey(), new HashSet<>(request.getValuesList()));
    setPutResponseBuilder.setStatus(CommonProtocol.Status.OK);

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.GetResponse get(SetProtocol.GetRequest request) {
    SetProtocol.GetResponse.Builder setGetResponseBuilder =
            SetProtocol.GetResponse.newBuilder();

    try {
      Set<String> values = getStore().sets().get(request.getKey());
      values.forEach(value -> setGetResponseBuilder.addValues(value));
      setGetResponseBuilder.setStatus(CommonProtocol.Status.OK);
    } catch (KeyNotFoundException e) {
      setGetResponseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    } catch (DstException e) {
      setGetResponseBuilder.setStatus(CommonProtocol.Status.UNKNOWN_ERROR);
    }

    return setGetResponseBuilder.build();
  }

  @Override
  public SetProtocol.DeleteResponse del(SetProtocol.DeleteRequest request) {
    SetProtocol.DeleteResponse.Builder setDeleteResponseBuilder =
            SetProtocol.DeleteResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = getStore().sets().del(request.getKey(), request.getEntity());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }

    setDeleteResponseBuilder.setStatus(status);

    return setDeleteResponseBuilder.build();
  }

  @Override
  public CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request) {
    CommonProtocol.DropResponse.Builder setDropByKeyResponseBuilder =
            CommonProtocol.DropResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().sets().dropByKey(request.getKey());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }

    setDropByKeyResponseBuilder.setStatus(status);
    return setDropByKeyResponseBuilder.build();
  }

  @Override
  public SetProtocol.ExistsResponse exists(SetProtocol.ExistsRequest request) {
    SetProtocol.ExistsResponse.Builder setExistResponseBuilder =
            SetProtocol.ExistsResponse.newBuilder();

    CommonProtocol.Status status;
    try {
      boolean result = getStore().sets().exists(request.getKey(), request.getEntity());
      setExistResponseBuilder.setResult(result);
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DstException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    setExistResponseBuilder.setStatus(status);
    return setExistResponseBuilder.build();
  }

}
