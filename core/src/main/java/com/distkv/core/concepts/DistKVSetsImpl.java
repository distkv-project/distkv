package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import java.util.Set;

public class DistKVSetsImpl extends DistKVConcepts<Set<String>> implements DistKVSets {

  public DistKVSetsImpl() {
  }

  @Override
  public void putItem(String key, String itemValue) {
    if (!distKVKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    distKVKeyValueMap.get(key).add(itemValue);
  }

  @Override
  public Status removeItem(String key, String itemValue) {
    if (!distKVKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    distKVKeyValueMap.get(key).remove(itemValue);
    return Status.OK;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!distKVKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return distKVKeyValueMap.get(key).contains(value);
  }

}
