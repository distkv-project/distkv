package org.dst.client;

import java.util.HashSet;
import java.util.Set;
import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.dst.rpc.service.DstSetService;

public class DstSetProxy {

  private DstSetService service;

  public DstSetProxy(DstSetService service) {
    this.service = service;
  }

  public void put(String key, Set<String> values) {
    SetProtocol.PutRequest.Builder request = SetProtocol.PutRequest.newBuilder();
    request.setKey(key);
    values.forEach(value -> request.addValues(value));

    SetProtocol.PutResponse response = service.put(request.build());

    if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public Set<String> get(String key) throws DstException {
    SetProtocol.GetRequest request =
            SetProtocol.GetRequest.newBuilder()
                    .setKey(key)
                    .build();

    SetProtocol.GetResponse response = service.get(request);
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

    SetProtocol.PutItemResponse response = service.putItem(request.build());
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

    SetProtocol.RemoveItemResponse response = service.removeItem(request.build());
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public boolean drop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);

    CommonProtocol.DropResponse response = service.drop(request.build());
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

    SetProtocol.ExistsResponse response = service.exists(request.build());

    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    return response.getResult();
  }

}
