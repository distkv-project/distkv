package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncIntProxy;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.IntProtocol;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.CompletableFuture;

public class DistkvIntProxy {

  private static final String typeCode = "F";

  private DistkvAsyncIntProxy asyncIntProxy;

  public DistkvIntProxy(DistkvAsyncIntProxy asyncIntProxy) {
    this.asyncIntProxy = asyncIntProxy;
  }

  public void put(
      String key, int value) throws DistkvException {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = asyncIntProxy.put(key, value);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(future);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public int get(
      String key) throws DistkvException, InvalidProtocolBufferException {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = asyncIntProxy.get(key);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(future);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    return response.getResponse().unpack(IntProtocol.IntGetResponse.class).getValue();
  }

  public void incr(
      String key, int delta) throws DistkvException {
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture =
        asyncIntProxy.incr(key, delta);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }
}
