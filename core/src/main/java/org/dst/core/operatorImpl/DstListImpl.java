package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstList;
import org.dst.exception.KeyNotFoundException;
import java.util.HashMap;
import java.util.List;

public class DstListImpl implements DstList {

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
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    return listMap.get(key);
  }

  @Override
  public void del(String key) {
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    listMap.remove(key);
  }

  @Override
  public void lput(String key, List<String> value) {
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    listMap.get(key).addAll(0, value);
  }

  @Override
  public void rput(String key, List<String> value) {
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    listMap.get(key).addAll(value);
  }

  @Override
  public void ldel(String key, int n) {
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    List<String> original = listMap.get(key);
    if (original.size() < n) {
      original.clear();
      return;
    }
    for (int i = 0; i < n; i++) {
      original.remove(0);
    }
  }

  @Override
  public void rdel(String key, int n) {
    if (!listMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    List<String> original = listMap.get(key);
    int size = original.size();
    if (size <= n) {
      original.clear();
      return;
    }
    for (int i = 0; i < n; i++) {
      original.remove(size - 1);
      size--;
    }
  }
}
