package com.distkv.core.concepts;

import com.distkv.common.utils.Status;

public interface DistkvInts {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the int value to store
   */
  void put(String key, Integer value);

  /**
   * This method will query a string value based on the key
   *
   * @param key obtain a int value based on the key
   * @return the int value
   */
  Integer get(String key);

  /**
   * This method will delete a string value based on the key
   *
   * @param key delete a key-value pair based on the key
   * @return The status of the drop operation.
   */
  Status drop(String key);

  /**
   * This method will increase the int value
   *
   * @param key increase a int value based on the key
   * @param delta the increased value
   */
  void incr(String key, int delta);

}
