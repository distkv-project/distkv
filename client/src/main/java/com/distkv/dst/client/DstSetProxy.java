package com.distkv.dst.client;

import java.util.HashSet;
import java.util.Set;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.service.DstSetService;

public class DstSetProxy {

  private DstSetService service;

  public DstSetProxy(DstSetService service) {
    this.service = service;
  }

  public void put(String key, Set<String> values) {
    SetProtocol.PutRequest.Builder request = SetProtocol.PutRequest.newBuilder();
    request.setKey(key);
    values.forEach(value -> request.addValues(value));

    SetProtocol.PutResponse response = FutureUtils.get(service.put(request.build()));
    if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public Set<String> get(String key) throws DstException {
    SetProtocol.GetRequest request =
            SetProtocol.GetRequest.newBuilder()
                    .setKey(key)
                    .build();

    SetProtocol.GetResponse response = FutureUtils.get(service.get(request));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    Set<String> set = new HashSet<>(response.getValuesList());
    return set;
  }

  public void putItem(String key, String entity) {
    SetProtocol.PutItemRequest.Builder request = SetProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemValue(entity);

    SetProtocol.PutItemResponse response = FutureUtils.get(service.putItem(request.build()));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void removeItem(String key, String entity) {
    SetProtocol.RemoveItemRequest.Builder request = SetProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemValue(entity);

    SetProtocol.RemoveItemResponse response = FutureUtils.get(
        service.removeItem(request.build()));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public boolean drop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);

    CommonProtocol.DropResponse response = FutureUtils.get(service.drop(request.build()));
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      return false;
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    return true;
  }

  public boolean exists(String key, String entity) {
    SetProtocol.ExistsRequest.Builder request = SetProtocol.ExistsRequest.newBuilder();
    request.setKey(key);
    request.setEntity(entity);

    SetProtocol.ExistsResponse response = FutureUtils.get(service.exists(request.build()));

    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    return response.getResult();
  }

}
