package com.distkv.dst.client;

import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import java.util.concurrent.CompletableFuture;

import static com.distkv.dst.client.CheckStatusUtil.checkStatus;

public class DstStringProxy {

  private DstStringService service;

  public DstStringProxy(DstStringService service) {
    this.service = service;
  }

  public void put(String key, String value) {
    StringProtocol.PutRequest request =
        StringProtocol.PutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();

    CompletableFuture<StringProtocol.PutResponse> responseFuture = service.put(request);
    StringProtocol.PutResponse response = FutureUtils.get(responseFuture);
    checkStatus(response.getStatus(), request.getKey());
  }

  public CompletableFuture<StringProtocol.PutResponse> asyncPut(
          String key, String value) {
    StringProtocol.PutRequest request = StringProtocol.PutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();
    CompletableFuture<StringProtocol.PutResponse> future = service.put(request);
    future.whenComplete((r, t) -> {
      if (t != null) {
        throw new IllegalStateException(t);
      } else {
        checkStatus(r.getStatus(), request.getKey());
      }
    });
    return future;
  }

  public String get(String key) throws DstException {
    StringProtocol.GetRequest request =
        StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();

    CompletableFuture<StringProtocol.GetResponse> responseFuture = service.get(request);
    StringProtocol.GetResponse response = FutureUtils.get(responseFuture);
    checkStatus(response.getStatus(), request.getKey());
    return response.getValue();
  }

  public CompletableFuture<StringProtocol.GetResponse> asyncGet(String key) {
    StringProtocol.GetRequest request = StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<StringProtocol.GetResponse> future = service.get(request);
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
    CommonProtocol.DropRequest request =
            CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();

    CompletableFuture<CommonProtocol.DropResponse> responseFuture = service.drop(request);
    CommonProtocol.DropResponse response = FutureUtils.get(responseFuture);
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
}
