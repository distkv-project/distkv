package com.distkv.core;

import java.util.HashMap;

public class DistkvHashMapImpl<K, V> implements DistkvMapInterface<K, V> {

  private HashMap<K, V> hashMap;

  public DistkvHashMapImpl() {
    this.hashMap = new HashMap<>();
  }

  @Override
  public void put(K key, V value) {
    hashMap.put(key, value);
  }

  @Override
  public boolean remove(K key) {
    try {
      hashMap.remove(key);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public V get(K key) {
    return hashMap.get(key);
  }

  @Override
  public boolean containsKey(K key) {
    return hashMap.containsKey(key);
  }

  @Override
  public void clear() {
    hashMap.clear();
  }
}
