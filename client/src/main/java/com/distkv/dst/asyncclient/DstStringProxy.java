package com.distkv.dst.asyncclient;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;

import java.util.concurrent.CompletableFuture;

public class DstStringProxy {
  private DstStringService service;

  public DstStringProxy(DstStringService service) {
        this.service = service;
    }

  public CompletableFuture<StringProtocol.PutResponse> asyncPut(
          String key, String value) {
    StringProtocol.PutRequest request = StringProtocol.PutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();
    CompletableFuture<StringProtocol.PutResponse> future = service.put(request);
    return future;
  }

  public CompletableFuture<StringProtocol.GetResponse> asyncGet(String key) {
    StringProtocol.GetRequest request = StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<StringProtocol.GetResponse> future = service.get(request);
    return future;
  }

  public CompletableFuture<CommonProtocol.DropResponse> asyncDrop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
            .setKey(key)
            .build();
    CompletableFuture<CommonProtocol.DropResponse> future = service.drop(request);
    return future;
  }
}
