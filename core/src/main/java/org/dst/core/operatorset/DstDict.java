package org.dst.core.operatorset;

import java.util.Map;

public interface DstDict {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the dictionary value to store
   */
  public void put(String key, Map<String, String> value);

  /**
   * This method will query a dictionary value based on the key
   *
   * @param key Obtain a dictionary value based on the key
   * @return the dictionary value
   */
  public Map<String, String> get(String key);

  /**
   * This method will delete a dictionary value based on the key
   *
   * @param key delete a key-value pair based on the key
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  public boolean del(String key);

}

