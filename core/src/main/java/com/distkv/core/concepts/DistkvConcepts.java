package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvHashMapImpl;
import com.distkv.core.DistkvMapInterface;

public abstract class DistkvConcepts<T> {

  protected DistkvMapInterface<String, T> distkvKeyValueMap = new DistkvHashMapImpl<>();

  public void put(String key, T value) {
    distkvKeyValueMap.put(key, value);
  }

  public T get(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return distkvKeyValueMap.get(key);
  }

  public Status drop(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    distkvKeyValueMap.remove(key);
    return Status.OK;
  }

}
