package com.distkv.asyncclient;

import static com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType.DROP;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.distkv.rpc.service.DistkvService;
import com.google.protobuf.Any;
import java.util.concurrent.CompletableFuture;

public class DistkvAsyncProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncProxy(DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvResponse> drop(String key) {
    DistkvRequest request = DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(DROP)
        .build();
    return call(request);
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> expire(String key, long expireTime) {
    ExpireRequest expireRequest = ExpireRequest
        .newBuilder()
        .setExpireTime(expireTime)
        .build();
    DistkvProtocol.DistkvRequest request = DistkvProtocol.DistkvRequest.newBuilder()
        .setKey(key)
        .setRequestType(RequestType.EXPIRED)
        .setRequest(Any.pack(expireRequest))
        .build();
    return call(request);
  }
}
