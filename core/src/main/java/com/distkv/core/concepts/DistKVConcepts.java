package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DstHashMapImpl;
import com.distkv.core.DstMapInterface;

public abstract class DistKVConcepts<T> {

  protected DstMapInterface<String, T> distKVKeyValueMap = new DstHashMapImpl<>();

  public void put(String key, T value) {
    distKVKeyValueMap.put(key, value);
  }

  public T get(String key) {
    if (!distKVKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return distKVKeyValueMap.get(key);
  }

  public Status drop(String key) {
    if (!distKVKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    distKVKeyValueMap.remove(key);
    return Status.OK;
  }

}
