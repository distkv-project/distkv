package org.dst.core.operatorImpl;

import org.dst.core.exception.NotImplementException;
import org.dst.core.operatorset.DstList;
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
    throw new NotImplementException("The method is not implemented");
  }

  @Override
  public boolean rput(String key, List<String> value) {
    throw new NotImplementException("The method is not implemented");
  }

  @Override
  public boolean ldel(String key, int n) {
    throw new NotImplementException("The method is not implemented");
  }

  @Override
  public boolean rdel(String key, int n) {
    throw new NotImplementException("The method is not implemented");
  }
}
