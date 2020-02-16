package com.distkv.core.map;

/**
 * this is a copy from airlift slice FnvHash.
 * https://github.com/airlift/slice/blob/master/src/main/java/io/airlift/slice/FnvHash.java
 */
public class FnvHash implements HashFunction {
  private static final long FNV_64_OFFSET_BASIS = 0xcbf29ce484222325L;
  private static final long FNV_64_PRIME = 0x100000001b3L;

  private static final int FNV_32_OFFSET_BASIS = 0x811c9dc5;
  private static final int FNV_32_PRIME = 0x01000193;

  FnvHash() {
  }

  public int hash32(byte[] data) {
    int hash = FNV_32_OFFSET_BASIS;

    for (byte bit : data) {
      hash ^= bit;
      hash *= FNV_32_PRIME;
    }

    return hash;
  }

  public long hash64(byte[] data) {
    long hash = FNV_64_OFFSET_BASIS;

    for (byte bit : data) {
      hash ^= bit;
      hash *= FNV_64_PRIME;
    }

    return hash;
  }
}

