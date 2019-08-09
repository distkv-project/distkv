package org.dst.core.operatorImpl;

import org.dst.core.exception.KeyNotFoundException;
import org.dst.core.operatorset.DstSet;
import java.util.HashMap;
import java.util.Set;

public class DstSetImpl implements DstSet {

  private HashMap<String, Set<String>> setMap;

  public DstSetImpl() {
    this.setMap = new HashMap<String, Set<String>>();
  }

  @Override
  public void put(String key, Set<String> value) {
    setMap.put(key, value);
  }

  @Override
  public Set<String> get(String key) {
    return setMap.get(key);
  }

  @Override
  public boolean del(String key) {
    if (!setMap.containsKey(key)) {
      return false;
    }

    setMap.remove(key);
    return true;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!setMap.containsKey(key)) {
      throw new KeyNotFoundException("The key is not found");
    }

    return setMap.get(key).contains(value);
  }

}
