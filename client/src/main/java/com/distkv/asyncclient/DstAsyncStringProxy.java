package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.rpc.service.DstStringService;

import java.util.concurrent.CompletableFuture;

public class DstAsyncStringProxy {
  private DstStringService service;

  public DstAsyncStringProxy(DstStringService service) {
    this.service = service;
  }

  public CompletableFuture<StringProtocol.PutResponse> put(
          String key, String value) {
    StringProtocol.PutRequest request = StringProtocol.PutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();
    CompletableFuture<StringProtocol.PutResponse> future = service.put(request);
    return future;
  }

  public CompletableFuture<StringProtocol.GetResponse> get(String key) {
    StringProtocol.GetRequest request = StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<StringProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> drop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    return future;
  }
}
