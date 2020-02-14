package com.distkv.core.map;

public interface HashFunction {

  long hash64(byte[] key);

  int hash32(byte[] key);
}
