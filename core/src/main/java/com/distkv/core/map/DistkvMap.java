package com.distkv.core.map;

/**
 * DistkvMap can't have duplicate keys, and each key can map to at mostly one value.
 * the key and value need to be Serialized and deserialize by users, because only
 * users known what type they wanted.
 *
 * @author meijie
 * @since 0.1.4
 */
public interface DistkvMap extends AutoCloseable {

  /**
   * get mapped value by key. if the key is not exists, null will returned.
   * @param key the key to find mapped value.
   * @return mapped value.
   */
  byte[] get(byte[] key);

  /**
   * put the key and associated value to this map. if the key exists,
   * the previous value will be overwritten.
   * @param key the key associated to the value which to put.
   * @param value the value associated to the key which to put.
   */
  void put(byte[] key, byte[] value);

  /**
   * put the key and associated value to this map. if the key exists, do nothing.
   * @param key the key associated to the value which to put.
   * @param value the value associated to the key which to put.
   */
  void putIfAbsent(byte[] key, byte[] value);

  /**
   * the key will expired with given time.
   * @param key the key associated to the value at this map.
   * @param expireTime the time to expire.
   */
  void expire(byte[] key, long expireTime);

  /**
   * remove the key and associated value from this map.
   * @param key the key specify which key value to remove.
   */
  void remove(byte[] key);

  /**
   * return the number of key value mappings at this map.
   * @return the number of key value mappings at this map.
   */
  int size();

  /**
   * return the set of the keys at this map.
   * @return the set of keys.
   */
  byte[][] keys();

  /**
   * return the set of the values at this map.
   * @return the set of values.
   */
  byte[][] values();
}
