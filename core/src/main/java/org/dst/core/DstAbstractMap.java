package org.dst.core;

public interface DstAbstractMap<K, V> {

  void put(K key, V value);

  V remove(K key);

  V get(K key);

  boolean containsKey(K key);

  void clear();

}
