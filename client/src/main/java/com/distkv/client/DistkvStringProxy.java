package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncStringProxy;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.concurrent.CompletableFuture;

public class DistkvStringProxy {

  private static final String typeCode = "A";

  private DistkvAsyncStringProxy asyncStrProxy;

  public DistkvStringProxy(DistkvAsyncStringProxy asyncStrProxy) {
    this.asyncStrProxy = asyncStrProxy;
  }

  public void put(String key, String value) throws DistkvException {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = asyncStrProxy.put(key, value);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(future);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  public String get(String key) throws DistkvException, InvalidProtocolBufferException {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = asyncStrProxy.get(key);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(future);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    return response.getResponse().unpack(StringProtocol.StrGetResponse.class).getValue();
  }

  public boolean drop(String key) {
    CompletableFuture<DistkvProtocol.DistkvResponse> responseFuture = asyncStrProxy.drop(key);
    DistkvProtocol.DistkvResponse response = FutureUtils.get(responseFuture);
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
    return true;
  }

  /**
   * Expire a key.
   *
   * @param key The key to be expired.
   * @param expireTime Millisecond level to set expire.
   */
  public void expire(String key, long expireTime) {
    DistkvResponse response = FutureUtils.get(asyncStrProxy.expire(key, expireTime));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

}
