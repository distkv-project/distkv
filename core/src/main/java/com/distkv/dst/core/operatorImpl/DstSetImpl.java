package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstSet;
import com.distkv.dst.core.DstMapInterface;
import com.distkv.dst.core.DstConcurrentHashMapImpl;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import java.util.Set;

public class DstSetImpl implements DstSet {

  private DstMapInterface<String, Set<String>> setMap;

  public DstSetImpl() {
    this.setMap = new DstConcurrentHashMapImpl<>();
  }

  @Override
  public void put(String key, Set<String> value) {
    setMap.put(key, value);
  }

  @Override
  public Set<String> get(String key) {
    if (!setMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return setMap.get(key);
  }

  @Override
  public void putItem(String key, String itemValue) {
    if (!setMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    setMap.get(key).add(itemValue);
  }

  @Override
  public Status drop(String key) {
    if (!setMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    setMap.remove(key);
    return Status.OK;
  }

  @Override
  public Status removeItem(String key, String itemValue) {
    if (!setMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    setMap.get(key).remove(itemValue);
    return Status.OK;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!setMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return setMap.get(key).contains(value);
  }

}
