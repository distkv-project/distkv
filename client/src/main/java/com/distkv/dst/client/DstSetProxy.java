package com.distkv.dst.client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.SetProtocol;
import com.distkv.dst.rpc.service.DstSetService;

import static com.distkv.dst.client.CheckStatusUtil.checkStatus;

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
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<SetProtocol.PutResponse> asyncPut(
          String key, Set<String> values) {
    SetProtocol.PutRequest request = SetProtocol.PutRequest.newBuilder()
            .setKey(key)
            .addAllValues(values)
            .build();
    CompletableFuture<SetProtocol.PutResponse> future = service.put(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public Set<String> get(String key) throws DstException {
    SetProtocol.GetRequest request =
            SetProtocol.GetRequest.newBuilder()
                    .setKey(key)
                    .build();

    SetProtocol.GetResponse response = FutureUtils.get(service.get(request));
    checkStatus(response.getStatus(), request.getKey());
    Set<String> set = new HashSet<>(response.getValuesList());
    return set;
  }

  public CompletableFuture<SetProtocol.GetResponse> asyncGet(String key) {
    SetProtocol.GetRequest request = SetProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<SetProtocol.GetResponse> future = service.get(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void putItem(String key, String entity) {
    SetProtocol.PutItemRequest.Builder request = SetProtocol.PutItemRequest.newBuilder();
    request.setKey(key);
    request.setItemValue(entity);

    SetProtocol.PutItemResponse response = FutureUtils.get(service.putItem(request.build()));
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<SetProtocol.PutItemResponse> asyncPutItem(
          String key, String entity) {
    SetProtocol.PutItemRequest request = SetProtocol.PutItemRequest.newBuilder()
            .setKey(key)
            .setItemValue(entity)
            .build();
    CompletableFuture<SetProtocol.PutItemResponse> future = service.putItem(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public void removeItem(String key, String entity) {
    SetProtocol.RemoveItemRequest.Builder request = SetProtocol.RemoveItemRequest.newBuilder();
    request.setKey(key);
    request.setItemValue(entity);

    SetProtocol.RemoveItemResponse response = FutureUtils.get(
        service.removeItem(request.build()));
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<SetProtocol.RemoveItemResponse> asyncRemoveItem(
          String key, String entity) {
    SetProtocol.RemoveItemRequest request = SetProtocol.RemoveItemRequest.newBuilder()
            .setKey(key)
            .setItemValue(entity)
            .build();
    CompletableFuture<SetProtocol.RemoveItemResponse> future = service.removeItem(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public boolean drop(String key) {
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);

    CommonProtocol.DropResponse response = FutureUtils.get(service.drop(request.build()));
    checkStatus(response.getStatus(), request.getKey());
    return true;
  }

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public boolean exists(String key, String entity) {
    SetProtocol.ExistsRequest.Builder request = SetProtocol.ExistsRequest.newBuilder();
    request.setKey(key);
    request.setEntity(entity);

    SetProtocol.ExistsResponse response = FutureUtils.get(service.exists(request.build()));
    checkStatus(response.getStatus(), request.getKey());
    return response.getResult();
  }

  public CompletableFuture<SetProtocol.ExistsResponse> asyncExists(
          String key, String entity) {
    SetProtocol.ExistsRequest request = SetProtocol.ExistsRequest.newBuilder()
            .setKey(key)
            .setEntity(entity)
            .build();
    CompletableFuture<SetProtocol.ExistsResponse> future = service.exists(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

}
