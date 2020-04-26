package com.distkv.pine.components.cache;

/**
 * The `PineCache` component is used as a cache.
 */
public interface PineCache {
  /**
   * Put a item into cache,and add a expireTime.
   *
   * @param item items is not null
   */
  public void newItem(String item);


  /**
   *  whether the key exsist in  cached .
   *
   * @param item the key if expired
   * @return true expired false not expired
   */
  public Boolean isExpired(String item);


}

