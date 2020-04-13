package com.distkv.pine.components.cache;


import com.distkv.common.DistkvTuple;
import com.distkv.pine.components.topper.TopperMember;
import java.util.List;

/**
 * The `PineCache` component is used as a cache.
 */
public interface PineCache {

  /**
   *
   * @param newItems
   */
  public void newItems(String newItems);


  /**
   *
   * @param expireTime
   * @param newItems
   * @return
   */
  public Boolean expireIf(long expireTime, String newItems);

  /**
   *
   * @param newItems
   * @return
   */
  public String getItem(String newItems);
}

