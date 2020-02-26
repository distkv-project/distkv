package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.distkv.rpc.service.DistkvService;

import com.google.protobuf.Any;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncIntProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncIntProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> put(
      String key, int value) {
    IntProtocol.IntPutRequest intPutRequest = IntProtocol.IntPutRequest.newBuilder()
        .setValue(value)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.INT_PUT)
        .setRequest(Any.pack(intPutRequest))
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> get(
      String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.INT_GET)
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> drop(
      String key) {
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.INT_DROP)
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> incr(
      String key, int delta) {
    IntProtocol.IntIncrRequest intIncrRequest = IntProtocol.IntIncrRequest.newBuilder()
        .setDelta(delta)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.INT_INCR)
        .setRequest(Any.pack(intIncrRequest))
        .build();
    return call(request);
  }
}
