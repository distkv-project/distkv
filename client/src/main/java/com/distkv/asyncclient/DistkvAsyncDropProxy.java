package com.distkv.asyncclient;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.service.DistkvService;

import java.util.concurrent.CompletableFuture;

public class DistkvAsyncDropProxy extends DistkvAbstractAsyncProxy {

  public DistkvAsyncDropProxy(
      DistkvAsyncClient client, DistkvService service) {
    super(client, service);
  }

  public CompletableFuture<DistkvResponse> drop(
      String key) {
    return drop(key, RequestType.DROP);
  }
}
