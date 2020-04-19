package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncSetProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncSetProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(String key, Set<String> values) {
    SetProtocol.SetPutRequest setPutRequest = SetProtocol.SetPutRequest.newBuilder()
        .addAllValues(values)
        .build();

    return put(key, RequestType.SET_PUT, Any.pack(setPutRequest));
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(String key) {
    return get(key, RequestType.SET_GET);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> putItem(String key, String entity) {
    SetProtocol.SetPutItemRequest setPutItemRequest = SetProtocol.SetPutItemRequest.newBuilder()
        .setItemValue(entity)
        .build();

    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_PUT_ITEM)
        .setRequest(Any.pack(setPutItemRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> removeItem(String key, String entity) {
    SetProtocol.SetRemoveItemRequest setRemoveItemRequest = SetProtocol.SetRemoveItemRequest
        .newBuilder()
        .setItemValue(entity)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_REMOVE_ITEM)
        .setRequest(Any.pack(setRemoveItemRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> exists(String key, String entity) {
    SetProtocol.SetExistsRequest setExistsRequest = SetProtocol.SetExistsRequest.newBuilder()
        .setEntity(entity)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.SET_EXISTS)
        .setRequest(Any.pack(setExistsRequest))
        .build();
    return call(request);
  }

}
