package org.dst.core;

import java.util.concurrent.ConcurrentHashMap;

public class DstConcurrentHashMap<K, V> implements DstAbstractMap<K, V> {

  private ConcurrentHashMap concurrentHashMap;

  public DstConcurrentHashMap() {
    this.concurrentHashMap = new ConcurrentHashMap();
  }

  @Override
  public void put(K key, V value) {
    concurrentHashMap.put(key, value);
  }

  @Override
  public void remove(K key) {
    concurrentHashMap.remove(key);
  }

  @Override
  public V get(K key) {
    return (V) concurrentHashMap.get(key);
  }

  @Override
  public boolean containsKey(K key) {
    return concurrentHashMap.containsKey(key);
  }
}
