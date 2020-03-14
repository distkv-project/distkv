package com.distkv.core.map;

import com.distkv.core.segment.IntSegment;
import com.distkv.core.segment.NonFixedSegment;

/**
 *
 * TODO　Collision detection, resize, rehash and so on.
 */
public class DistkvNonHeapHashMapImpl {

  private IntSegment hashSegment = new IntSegment(1);
  private NonFixedSegment valueSegment = new NonFixedSegment(1);
  private HashFunction hashFunction = new FnvHash();

  private static final int DEFAULT_CAPACITY = 64;
  private int capacity = DEFAULT_CAPACITY;

  public void put(byte[] key, byte[] value) {
    int hash = hashFunction.hash32(key);
    int pointer = valueSegment.addKeyValue(key, value);
    int keyPointer = hash % capacity;
    hashSegment.put(keyPointer, pointer);
  }

  public byte[] get(byte[] key) {
    int hash = hashFunction.hash32(key);
    int keyPointer = hash % capacity;
    int pointer = hashSegment.get(keyPointer);
    return valueSegment.getKeyValue(pointer)[1];
  }
}
