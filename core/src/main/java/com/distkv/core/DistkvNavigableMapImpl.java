package com.distkv.core;

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.util.Map.Entry;

public class DistkvNavigableMapImpl<K, V>
    implements DistkvNavigableMap<K, V> {

  private NavigableMap<K, V> navigableMap;

  public DistkvNavigableMapImpl() {
    this.navigableMap = new ConcurrentSkipListMap<>();
  }

  public DistkvNavigableMapImpl(Comparator<? super K> comparator) {
    this.navigableMap = new ConcurrentSkipListMap<>(comparator);
  }

  @Override
  public K lowerKey(K key) {
    return this.navigableMap.lowerKey(key);
  }

  @Override
  public Entry<K, V> lowerEntry(K key) {
    return this.navigableMap.lowerEntry(key);
  }

  @Override
  public K higherKey(K key) {
    return this.navigableMap.higherKey(key);
  }

  @Override
  public Entry<K, V> higherEntry(K key) {
    return this.navigableMap.higherEntry(key);
  }

  @Override
  public K firstKey() {
    return this.navigableMap.firstKey();
  }

  @Override
  public Entry<K, V> firstEntry() {
    return this.navigableMap.firstEntry();
  }

  @Override
  public K lastKey() {
    return this.navigableMap.lastKey();
  }

  @Override
  public Entry<K, V> lastEntry() {
    return this.navigableMap.lastEntry();
  }

  @Override
  public void put(K key, V value) {
    this.navigableMap.put(key, value);
  }

  @Override
  public boolean remove(K key) {
    return this.navigableMap.remove(key) != null;
  }

  @Override
  public V get(K key) {
    return this.navigableMap.get(key);
  }

  @Override
  public boolean containsKey(K key) {
    return this.navigableMap.containsKey(key);
  }

  @Override
  public void clear() {
    this.navigableMap.clear();
  }
}
