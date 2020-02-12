package com.distkv.core.concepts;

import com.distkv.common.utils.Status;

public interface DistkvStrings {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the string value to store
   */
  void put(String key, String value);

  /**
   * This method will query a string value based on the key
   *
   * @param key Obtain a string value based on the key
   * @return the string  value
   */
  String get(String key);

  /**
   * This method will delete a string value based on the key
   *
   * @param key delete a key-value pair based on the key
   * @return The status of the drop operation.
   */
  Status drop(String key);

}
