package org.dst.core.operatorImpl;

import org.dst.core.DstAbstractMap;
import org.dst.core.DstConcurrentHashMap;
import org.dst.core.operatorset.DstString;

public class DstStringImpl implements DstString {

  private DstAbstractMap<String, String> strMap;

  public DstStringImpl() {
    this.strMap = new DstConcurrentHashMap<>();
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
  public boolean del(String key) {
    if (!strMap.containsKey(key)) {
      return false;
    }

    strMap.remove(key);
    return true;
  }

}
