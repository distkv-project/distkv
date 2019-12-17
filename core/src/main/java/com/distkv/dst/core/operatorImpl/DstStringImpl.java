package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstString;
import com.distkv.dst.core.DstMapInterface;
import com.distkv.dst.core.DstHashMapImpl;

public class DstStringImpl implements DstString {

  private DstMapInterface<String, String> strMap;

  public DstStringImpl() {
    this.strMap = new DstHashMapImpl<>();
  }

  @Override
  public void put(String key, String kalue) {
    strMap.put(key, kalue);
  }

  @Override
  public String get(String key) {
    if (!strMap.containsKey(key)) {
      return null;
    }

    return strMap.get(key);
  }

  @Override
  public boolean drop(String key) {
    if (!strMap.containsKey(key)) {
      return false;
    }

    strMap.remove(key);
    return true;
  }

}
