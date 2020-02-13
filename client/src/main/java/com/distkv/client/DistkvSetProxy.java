package com.distkv.client;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetExistsResponse;
import com.distkv.rpc.protobuf.generated.SetProtocol.SetGetResponse;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashSet;
import java.util.Set;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.SetProtocol;

public class DistkvSetProxy {

  private String typeCode = "C";

  private DistkvService service;

  public DistkvSetProxy(DistkvService service) {
    this.service = service;
  }

  public void put(String key, Set<String> values) {
    SetProtocol.SetPutRequest.Builder setPutRequest = SetProtocol.SetPutRequest.newBuilder();
    values.forEach(setPutRequest::addValues);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_PUT)
        .setRequest(Any.pack(setPutRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public Set<String> get(String key) throws DistkvException {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_GET)
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return new HashSet<>(response.getResponse().unpack(SetGetResponse.class).getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }

  public void putItem(String key, String entity) {
    SetProtocol.SetPutItemRequest.Builder setPutItemRequest = SetProtocol.SetPutItemRequest
        .newBuilder();
    setPutItemRequest.setItemValue(entity);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_PUT_ITEM)
        .setRequest(Any.pack(setPutItemRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public void removeItem(String key, String entity) {
    SetProtocol.SetRemoveItemRequest.Builder setRemoveItemRequest =
        SetProtocol.SetRemoveItemRequest
            .newBuilder();
    setRemoveItemRequest.setItemValue(entity);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_REMOVE_ITEM)
        .setRequest(Any.pack(setRemoveItemRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
  }

  public boolean drop(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_DROP)
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    return true;
  }

  public boolean exists(String key, String entity) {
    SetProtocol.SetExistsRequest.Builder setExistsRequest =
        SetProtocol.SetExistsRequest.newBuilder();
    setExistsRequest.setEntity(entity);
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_EXISTS)
        .setRequest(Any.pack(setExistsRequest.build()))
        .build();
    DistkvResponse response = FutureUtils.get(service.call(request));
    CheckStatusUtil.checkStatus(response.getStatus(), request.getKey(), typeCode);
    try {
      return response.getResponse().unpack(SetExistsResponse.class).getResult();
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException(e.toString());
    }
  }
}
