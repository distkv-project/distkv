package com.distkv.pine.components.cache;

/**
 * The `PineCache` component is used as a cache.
 */
public interface PineCache {

  /**
   * Put a newItems in to cache.
   *
   * @param newItems newItems is not null
   */
  public void newItems(String newItems);


  /**
   * Key(newItems) if or not exsit in the cache.
   *
   * @param newItems newItems is the key if expired
   * @return true expired false not expired
   */
  public Boolean expireIf(String newItems);

  /**
   * Get newItems from cache.
   *
   * @param newItems getItem which key is newItems
   * @return get value which key is newItems
   */
  public String getItem(String newItems);
}

