package org.dst.client;

import java.util.HashSet;
import java.util.Set;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.generated.SetProtocol;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.service.DstSetService;

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

  public void delete(String key, String entity) {
    SetProtocol.DeleteRequest.Builder request = SetProtocol.DeleteRequest.newBuilder();
    request.setKey(key);
    request.setEntity(entity);

    SetProtocol.DeleteResponse response = service.delete(request.build());
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public boolean dropByKey(String key) {
    SetProtocol.DropByKeyRequest.Builder request = SetProtocol.DropByKeyRequest.newBuilder();
    request.setKey(key);

    SetProtocol.DropByKeyResponse response = service.dropByKey(request.build());
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
