package com.distkv.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.server.base.DstBaseService;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.common.utils.Status;
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
  public SetProtocol.PutItemResponse putItem(SetProtocol.PutItemRequest request) {
    SetProtocol.PutItemResponse.Builder builder = SetProtocol.PutItemResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      getStore().sets().putItem(request.getKey(), request.getItemValue());
      status = CommonProtocol.Status.OK;
    } catch (KeyNotFoundException e) {
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    }
    builder.setStatus(status);
    return builder.build();
  }

  @Override
  public SetProtocol.RemoveItemResponse removeItem(SetProtocol.RemoveItemRequest request) {
    SetProtocol.RemoveItemResponse.Builder setDeleteResponseBuilder =
            SetProtocol.RemoveItemResponse.newBuilder();

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;

    try {
      Status localStatus = getStore().sets().removeItem(
          request.getKey(), request.getItemValue());
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
      Status localStatus = getStore().sets().drop(request.getKey());
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
