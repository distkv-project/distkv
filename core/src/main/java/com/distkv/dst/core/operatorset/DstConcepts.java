package com.distkv.dst.core.operatorset;

import com.distkv.dst.core.DstMapInterface;

public abstract class DstConcepts<T> {

  protected DstMapInterface<String, T> dstKeyValueMap;

  public boolean drop(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      return false;
    }

    dstKeyValueMap.remove(key);
    return true;
  }

}
