package com.distkv.server.storeserver.cluster;

import com.alipay.sofa.jraft.Closure;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;

import java.util.concurrent.CompletableFuture;

/**
 * @author : kairbon
 * @date : 2021/3/22
 */
public abstract class KVClosure implements Closure {

  DistkvProtocol.DistkvRequest request;

  CompletableFuture<DistkvProtocol.DistkvResponse> future;

  public DistkvProtocol.DistkvRequest getRequest() {
    return request;
  }

  public void setRequest(DistkvProtocol.DistkvRequest request) {
    this.request = request;
  }

  public CompletableFuture<DistkvProtocol.DistkvResponse> getFuture() {
    return future;
  }

  public void setFuture(CompletableFuture<DistkvProtocol.DistkvResponse> future) {
    this.future = future;
  }

}
