package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class DstListImpl implements DstList {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstListImpl.class.getName());

  private HashMap<String, List<String>> listMap;

  public DstListImpl() {
    this.listMap = new HashMap<String, List<String>>();
  }

  @Override
  public void put(String key, List<String> value) {
    listMap.put(key, value);
  }

  @Override
  public List<String> get(String key) {
    return listMap.get(key);
  }

  @Override
  public boolean del(String key) {
    if (!listMap.containsKey(key)) {
      return false;
    }

    listMap.remove(key);
    return true;
  }

  @Override
  public boolean lput(String key, List<String> value) {
    if (listMap.containsKey(key)) {
      value.addAll(listMap.get(key));
    }
    return listMap.put(key, value).size() > 0;
  }

  @Override
  public boolean rput(String key, List<String> value) {
    if (listMap.containsKey(key)) {
      return listMap.get(key).addAll(value);
    }
    return listMap.put(key,value).size() > 0;
  }

  @Override
  public boolean ldel(String key, int n) {
    if (listMap.containsKey(key)) {
      List<String> original = listMap.get(key);
      if (original.size() < n) {
        original.clear();
        return true;
      }
      for (int i = 0; i < n; i++) {
        original.remove(0);
      }
      return true;
    }
    LOGGER.info(" no key find to ldel for kvstore-list()");
    return false;
  }

  @Override
  public boolean rdel(String key, int n) {
    if (listMap.containsKey(key)) {
      List<String> original = listMap.get(key);
      int size = original.size();
      if (size <= n) {
        original.clear();
        return true;
      }
      for (int i = 0; i < n; i++) {
        original.remove(size - 1);
        size--;
      }
      return true;
    }
    LOGGER.info("no key find to rdel for kvstore-list()");
    return false;
  }
}
