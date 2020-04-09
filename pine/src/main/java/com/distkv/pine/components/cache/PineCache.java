package com.distkv.pine.components.cache;


public interface PineCache {
  /**
   * set key expireTime
   * @param topic The topic name.
   * @return The PipeCacheTopic object.
   */
  public PineCacheTopic topic(String topic);

}
