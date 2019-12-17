package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstConcepts;
import com.distkv.dst.core.operatorset.DstSet;
import com.distkv.dst.core.DstHashMapImpl;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import java.util.Set;

public class DstSetImpl extends DstConcepts<Set<String>> implements DstSet {

  public DstSetImpl() {
    dstKeyValueMap = new DstHashMapImpl<>();
  }

  @Override
  public void put(String key, Set<String> value) {
    dstKeyValueMap.put(key, value);
  }

  @Override
  public Set<String> get(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return dstKeyValueMap.get(key);
  }

  @Override
  public void putItem(String key, String itemValue) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    dstKeyValueMap.get(key).add(itemValue);
  }

  @Override
  public Status removeItem(String key, String itemValue) {
    if (!dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    dstKeyValueMap.get(key).remove(itemValue);
    return Status.OK;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return dstKeyValueMap.get(key).contains(value);
  }

}
