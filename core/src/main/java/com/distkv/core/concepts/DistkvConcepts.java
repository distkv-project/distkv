package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;

public abstract class DistkvConcepts<T> implements DistkvBaseOperation<T> {

  // The Reference of the key value map.
  protected DistkvMapInterface<String, Object> distkvKeyValueMap;

  protected DistkvConcepts(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    this.distkvKeyValueMap = distkvKeyValueMap;
  }

  @Override
  public void put(String key, T value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, value);
  }

  @Override
  public T get(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return (T) distkvKeyValueMap.get(key);
  }

  @Override
  public Status drop(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    distkvKeyValueMap.remove(key);
    return Status.OK;
  }

}
