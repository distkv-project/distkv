package org.dst.core.operatorImpl;

import org.dst.core.DstMapInterface;
import org.dst.core.DstConcurrentHashMapImpl;
import org.dst.core.operatorset.DstString;

public class DstStringImpl implements DstString {

  private DstMapInterface<String, String> strMap;

  public DstStringImpl() {
    this.strMap = new DstConcurrentHashMapImpl<>();
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
