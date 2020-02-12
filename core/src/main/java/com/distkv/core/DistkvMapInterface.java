package com.distkv.core;

public interface DistkvMapInterface<K, V> {

  /**
   * @param key   key with which the specified value is to be associated.
   * @param value value to be associated with the specified key.
   */
  void put(K key, V value);

  /**
   * @param key the key that needs to be removed.
   * @return a boolean value, which indicates whether the remove operation was successful.
   */
  boolean remove(K key);

  /**
   * Returns the value to which the specified key is mapped.
   * If this map contains no mapping for the key, return null.
   */
  V get(K key);

  /**
   * @param  key a key to search for
   * @return a boolean value, which indicates whether the key was contained.
   * */
  boolean containsKey(K key);


  /**
   * Removes all of the mappings from this map.
   */
  void clear();

}
