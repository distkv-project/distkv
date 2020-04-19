package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;

import java.util.concurrent.CompletableFuture;

public class DistkvAsyncIntProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncIntProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvResponse> put(
      String key, int value) {
    IntProtocol.IntPutRequest intPutRequest = IntProtocol.IntPutRequest
        .newBuilder()
        .setValue(value)
        .build();
    return put(key, RequestType.INT_PUT, Any.pack(intPutRequest));
  }

  public CompletableFuture<DistkvResponse> get(String key) {
    return get(key, RequestType.INT_GET);
  }

  public CompletableFuture<DistkvResponse> incr(
      String key, int delta) {
    IntProtocol.IntIncrRequest intIncrRequest = IntProtocol.IntIncrRequest.newBuilder()
        .setDelta(delta)
        .build();
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.INT_INCR)
        .setRequest(Any.pack(intIncrRequest))
        .build();
    return call(request);
  }
}
