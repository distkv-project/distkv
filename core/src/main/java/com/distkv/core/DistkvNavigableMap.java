package com.distkv.core;

import static java.util.Map.Entry;

public interface DistkvNavigableMap<K, V> extends DistkvMapInterface<K, V> {

  K lowerKey(K key);

  Entry<K, V> lowerEntry(K key);

  K higherKey(K key);

  Entry<K, V> higherEntry(K key);

  K firstKey();

  Entry<K, V> firstEntry();

  K lastKey();

  Entry<K, V> lastEntry();

}
