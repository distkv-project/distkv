package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DstHashMapImpl;
import com.distkv.core.DstMapInterface;

public abstract class DstConcepts<T> {

  protected DstMapInterface<String, T> dstKeyValueMap = new DstHashMapImpl<>();

  public void put(String key, T value) {
    dstKeyValueMap.put(key, value);
  }

  public T get(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return dstKeyValueMap.get(key);
  }

  public Status drop(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    dstKeyValueMap.remove(key);
    return Status.OK;
  }

}
