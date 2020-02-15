package com.distkv.server.storeserver.services;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.service.DistkvService;
import com.distkv.server.storeserver.runtime.StoreRuntime;

import java.util.concurrent.CompletableFuture;

public class DistkvServiceImpl implements DistkvService {

  private StoreRuntime storeRuntime;

  public DistkvServiceImpl(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
  }

  @Override
  public CompletableFuture<DistkvProtocol.DistkvResponse> call(
      DistkvProtocol.DistkvRequest request) {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = new CompletableFuture<>();
    storeRuntime.getWorkerPool().postRequest(request, future);
    return future;
  }

}
