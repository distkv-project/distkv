package org.dst.core.operatorset;

import java.util.Map;

public interface DstDict {

  /**
   * Put a key-value pair to map.
   *
   * @param key The key to store.
   * @param value The dictionary value to store.
   */
  void put(String key, Map<String, String> value);

  /**
   * Get a dictionary value by the given key.
   *
   * @param key The key that we will get its value.
   * @return the dictionary value
   */
  Map<String, String> get(String key);

  /**
   * Delete a dictionary entry by the given key.
   *
   * @param key The key of the entry that we will delete.
   * @return True if we succeed to delete, otherwise it's false.
   */
  boolean drop(String key);
}

