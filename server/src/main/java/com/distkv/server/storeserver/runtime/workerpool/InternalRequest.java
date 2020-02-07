package com.distkv.server.storeserver.runtime.workerpool;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import java.util.concurrent.CompletableFuture;

public class InternalRequest {

  private DistkvProtocol.DistkvRequest request;

  private CompletableFuture<DistkvResponse> completableFuture;

  public InternalRequest(DistkvProtocol.DistkvRequest request,
      CompletableFuture<DistkvProtocol.DistkvResponse> completableFuture) {
    this.request = request;
    this.completableFuture = completableFuture;
  }

  public DistkvProtocol.DistkvRequest getRequest() {
    return request;
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> getCompletableFuture() {
    return completableFuture;
  }

}
