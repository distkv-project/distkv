package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;

public abstract class DistkvConcepts<T> {

  // The Reference of the key value map.
  protected DistkvMapInterface<String, DistkvValue<T>> distkvKeyValueMap;

  protected DistkvConcepts(DistkvMapInterface<String, DistkvValue<T>> distkvKeyValueMap) {
    this.distkvKeyValueMap = distkvKeyValueMap;
  }

  public void put(String key, DistkvValue value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, value);
  }

  public abstract T as(DistkvValue distkvValue);

  public T get(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    return as(distkvKeyValueMap.get(key));
  }

  public Status drop(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    distkvKeyValueMap.remove(key);
    return Status.OK;
  }

}
