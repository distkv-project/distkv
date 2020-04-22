package com.distkv.core.concepts;

import com.distkv.common.utils.Status;
import java.util.Map;

public interface DistkvDicts {

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
   * @return The status of this drop operation.
   */
  Status drop(String key);
}

