package com.distkv.pine.components.cache;

/**
 * The `PineCache` component is used as a cache.
 */
public interface PineCache {

  /**
   * Put a newItem into cache.
   *
   * @param item newItems is not null
   */
  public void newItem(String item);


  /**
   * Key(item) if or not exsit in the cache.
   *
   * @param item item is the key if expired
   * @return true expired false not expired
   */
  public Boolean isExpired(String item);

  /**
   * Get newItems from cache.
   *
   * @param newItems getItem which key is newItems
   * @return get value which key is newItems
   */
  public String getItem(String newItems);
}

