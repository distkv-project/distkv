package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.SetProtocol;
import com.distkv.rpc.service.DstSetService;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DstAsyncSetProxy {

  private DstSetService service;

  public DstAsyncSetProxy(DstSetService service) {
    this.service = service;
  }

  public CompletableFuture<SetProtocol.PutResponse> put(
          String key, Set<String> values) {
    SetProtocol.PutRequest request = SetProtocol.PutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<SetProtocol.PutResponse> future = service.put(request);
    return future;
  }

  public CompletableFuture<SetProtocol.GetResponse> get(String key) {
    SetProtocol.GetRequest request = SetProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<SetProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<SetProtocol.PutItemResponse> putItem(
          String key, String entity) {
    SetProtocol.PutItemRequest request = SetProtocol.PutItemRequest.newBuilder()
            .setKey(key)
            .setItemValue(entity)
            .build();
    CompletableFuture<SetProtocol.PutItemResponse> future = service.putItem(request);
    return future;
  }

  public CompletableFuture<SetProtocol.RemoveItemResponse> removeItem(
          String key, String entity) {
    SetProtocol.RemoveItemRequest request = SetProtocol.RemoveItemRequest.newBuilder()
            .setKey(key)
            .setItemValue(entity)
            .build();
    CompletableFuture<SetProtocol.RemoveItemResponse> future = service.removeItem(request);
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> drop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    return future;
  }

  public CompletableFuture<SetProtocol.ExistsResponse> exists(
          String key, String entity) {
    SetProtocol.ExistsRequest request = SetProtocol.ExistsRequest.newBuilder()
            .setKey(key)
            .setEntity(entity)
            .build();
    CompletableFuture<SetProtocol.ExistsResponse> future = service.exists(request);
    return future;
  }
}
