package com.distkv.pine.runtime;

import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.PineRuntimeShutdownFailedException;
import com.distkv.pine.components.cache.PineCache;
import com.distkv.pine.components.cache.PineCacheImpl;
import com.distkv.pine.components.liker.PineLiker;
import com.distkv.pine.components.liker.PineLikerImpl;
import com.distkv.pine.components.topper.PineTopper;
import com.distkv.pine.components.topper.PineTopperImpl;

public class PineRuntime {


  /**
   * The distkv sync client.
   */
  private DistkvClient distkvClient;

  public void init(String address) {
    distkvClient = new DefaultDistkvClient(address);
  }

  public void shutdown() {
    try {
      distkvClient.disconnect();
    } catch (DistkvException e) {
      throw new PineRuntimeShutdownFailedException(
          String.format("Failed shutdown the client : %s", e.getMessage()));
    }
  }

  public PineTopper newTopper() {
    return new PineTopperImpl(distkvClient);
  }

  public PineLiker newLiker() {
    return new PineLikerImpl(distkvClient);
  }

  public PineCache newCache(Long expireTime) {
    return new PineCacheImpl(distkvClient, expireTime);
  }

}
