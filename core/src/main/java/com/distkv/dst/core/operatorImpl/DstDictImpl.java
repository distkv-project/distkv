package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstConcepts;
import com.distkv.dst.core.operatorset.DstDict;
import com.distkv.dst.core.DstHashMapImpl;
import java.util.Map;

public class DstDictImpl extends DstConcepts<Map<String, String>> implements DstDict {

  public DstDictImpl() {
    dstKeyValueMap = new DstHashMapImpl<>();
  }

  @Override
  public void put(String key, Map<String, String> value) {
    dstKeyValueMap.put(key, value);
  }

  @Override
  public Map<String, String> get(String key) {
    return dstKeyValueMap.get(key);
  }

}
