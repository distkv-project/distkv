package com.distkv.core;

import static java.util.Map.Entry;

/**
 * A map store ordered key-value pairs.
 *
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
public interface DistkvNavigableMap<K, V> extends DistkvMapInterface<K, V> {

  /**
   * Return the greatest key which less than the given key.
   * @param key the key
   * @return the greatest key which less than the given key.
   */
  K lowerKey(K key);

  /**
   * Return the entry which's key is the greatest key less than the given key.
   * @param key the key
   * @return the entry which's key is the greatest key less than the given key.
   */
  Entry<K, V> lowerEntry(K key);

  /**
   * Return the lowest key which is higher than given key.
   * @param key the key
   * @return the lowest key which is higher than given key.
   */
  K higherKey(K key);

  /**
   * Return the entry which's key is the lowest key higher than given key.
   * @param key the key.
   * @return the entry which's key is the lowest key higher than given key.
   */
  Entry<K, V> higherEntry(K key);

  /**
   * Return the lowest key at this map.
   * @return the lowest key at this map.
   */
  K firstKey();

  /**
   * Return the entry which's key is the lowest key at this map.
   * @return the entry which's key is the lowest key at this map.
   */
  Entry<K, V> firstEntry();

  /**
   * Return the greatest key at this map.
   * @return the greatest key at this map.
   */
  K lastKey();

  /**
   * Return the entry which's key is the greatest key at this map.
   * @return the entry which's key is the greatest key at this map.
   */
  Entry<K, V> lastEntry();

}
