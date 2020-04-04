package com.distkv.pine.components.cache;

public interface PineCache {



  /**
   * Add a member to this topper.
   *
   * @param newItems The string whiech put in cache.
   * .
   */
  public void newItem(String newItems);

  /**
   * Add a member to this topper.
   * @param newItems The string whiech put in cache.
   * @param expireTime expireTime.
   * .
   */
  public void newItem(String newItems, long expireTime);

  /**
   * Add a member to this topper.
   * @param newItems expireTime.
   * .
   */
  public Boolean expire(String newItems);




}
