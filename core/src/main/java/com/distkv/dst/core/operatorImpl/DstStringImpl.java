package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstConcepts;
import com.distkv.dst.core.operatorset.DstString;
import com.distkv.dst.core.DstHashMapImpl;

public class DstStringImpl extends DstConcepts<String> implements DstString {

  public DstStringImpl() {
    this.dstKeyValueMap = new DstHashMapImpl<>();
  }

  @Override
  public void put(String key, String value) {
    this.dstKeyValueMap.put(key, value);
  }

  @Override
  public String get(String key) {
    if (!this.dstKeyValueMap.containsKey(key)) {
      return null;
    }

    return this.dstKeyValueMap.get(key);
  }

}
