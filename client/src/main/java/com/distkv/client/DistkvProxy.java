package com.distkv.client;

import com.distkv.asyncclient.DistkvAsyncProxy;
import com.distkv.common.utils.FutureUtils;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;

public class DistkvProxy {

  private static final String typeCode = "X";

  private DistkvAsyncProxy asyncProxy;

  public DistkvProxy(DistkvAsyncProxy asyncProxy) {
    this.asyncProxy = asyncProxy;
  }


  /**
   * Drop the k-v pair.
   *
   * @param key The key to be dropped.
   */
  public void drop(String key) {
    DistkvResponse response = FutureUtils.get(asyncProxy.drop(key));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  /**
   * Expire a key.
   *
   * @param key The key to be expired.
   * @param expireTime Millisecond level to set expire.
   */
  public void expire(String key, long expireTime) {
    DistkvResponse response = FutureUtils.get(asyncProxy.expire(key, expireTime));
    CheckStatusUtil.checkStatus(response.getStatus(), key, typeCode);
  }

  //TODO (senyer) add ttl
}
