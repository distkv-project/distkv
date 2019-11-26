package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstDict;
import com.distkv.dst.core.DstMapInterface;
import com.distkv.dst.core.DstConcurrentHashMapImpl;
import java.util.Map;

public class DstDictImpl implements DstDict {

  private DstMapInterface<String, Map<String, String>> dictMap;

  public DstDictImpl() {
    this.dictMap = new DstConcurrentHashMapImpl<>();
  }

  @Override
  public void put(String key, Map<String, String> value) {
    dictMap.put(key, value);
  }

  @Override
  public Map<String, String> get(String key) {
    return dictMap.get(key);
  }

  @Override
  public boolean drop(String key) {
    if (!dictMap.containsKey(key)) {
      return false;
    }

    dictMap.remove(key);
    return true;
  }
}
