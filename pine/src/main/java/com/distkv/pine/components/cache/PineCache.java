package com.distkv.pine.components.cache;
git
/**
 * The `PineCache` component is used as a cache.
 */
public interface PineCache {

  /**
   * put a newItems in to cache
   *
   * @param newItems newItems is not null
   */
  public void newItems(String newItems);


  /**
   * if newTimes is expired
   * @param expireTime expireTime
   * @param newItems newItems is the key if expired
   * @return true expired false not expired
   */
  public Boolean expireIf(long expireTime, String newItems);

  /**
   * get newItems from cache
   *
   * @param newItems getItem which key is newItems
   * @return get value which key is newItems
   */
  public String getItem(String newItems);
}

