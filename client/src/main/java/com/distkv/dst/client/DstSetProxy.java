package com.distkv.dst.client;

import java.util.HashSet;
import java.util.Set;
import com.distkv.dst.common.exception.DstException;
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
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey());
  }

  public Set<String> get(String key) throws DstException {
    SetProtocol.GetRequest request =
            SetProtocol.GetRequest.newBuilder()
                    .setKey(key)
                    .build();

    SetProtocol.GetResponse response = FutureUtils.get(service.get(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey());
    Set<String> set = new HashSet<>(response.getValuesList());
    return set;
  }

  public void putItem(String key, String entity) {
    SetProtocol.PutItemRequest.Builder request = SetProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemValue(entity);

    SetProtocol.PutItemResponse response = FutureUtils.get(service.putItem(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey());
  }

  public void removeItem(String key, String entity) {
    SetProtocol.RemoveItemRequest.Builder request = SetProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemValue(entity);

    SetProtocol.RemoveItemResponse response = FutureUtils.get(
        service.removeItem(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey());
  }

  public boolean drop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);

    CommonProtocol.DropResponse response = FutureUtils.get(service.drop(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey());
    return true;
  }

  public boolean exists(String key, String entity) {
    SetProtocol.ExistsRequest.Builder request = SetProtocol.ExistsRequest.newBuilder();
    request.setKey(key);
    request.setEntity(entity);

    SetProtocol.ExistsResponse response = FutureUtils.get(service.exists(request.build()));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey());
    return response.getResult();
  }
}
