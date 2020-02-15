package com.distkv.core.map;

/**
 *
 * TODO　Collision detection, resize, rehash and so on.
 */
public class DistNonHeapHashMapImpl {

  private IntSegment hashSegment = new IntSegment(1);
  private NonFixedSegment valueSegment = new NonFixedSegment(1);

  private HashFunction hashFunction = new FnvHash();
  private int capacity = 64;

  public void put(byte[] key, byte[] value) {
    int hash = hashFunction.hash32(key);
    int pointer = valueSegment.addKeyValue(key, value);
    int keyPointer = hash % capacity;
    hashSegment.putValue(keyPointer, pointer);
  }


  public byte[] get(byte[] key) {
    int hash = hashFunction.hash32(key);
    int keyPointer = hash % capacity;
    int pointer = hashSegment.getValue(keyPointer);
    return valueSegment.getKeyValue(pointer)[1];
  }
}
