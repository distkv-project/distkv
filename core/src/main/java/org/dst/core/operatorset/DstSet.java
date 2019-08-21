package org.dst.core.operatorset;

import org.dst.exception.KeyNotFoundException;
import org.dst.utils.Status;

import java.util.Set;

public interface DstSet {

  /**
   * This method will put a key-value pair to map
   *
   * @param key   the key to store
   * @param value the set value to store
   */
  void put(String key, Set<String> value);

  /**
   * This method will query a set value based on the key
   *
   * @param key Obtain a set value based on the key
   * @return the set value
   */
  Set<String> get(String key);

  /**
   * This method will delete the whole set based on the key
   *
   * @param key delete the whole set based on the key
   * @return Status indicates that the deletion succeeded or failed.
   */
  Status dropByKey(String key);

  /**
   * This method will delete a value according to the key
   *
   * @param key the key existing in set
   * @param value the value will be deleted
   * @return Status indicates that the deletion succeeded or failed.
   * */
  Status del(String key, String value);

  /**
   * This method will judge that if the value exists in map or not.
   *
   * @param key   the key exists in map
   * @param value the set value you want to judge
   * @return Status indicates that the value exists or not.
   * @throws KeyNotFoundException If the key don't exist in map
   */
  boolean exists(String key, String value) throws KeyNotFoundException;

}
