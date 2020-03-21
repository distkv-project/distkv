package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.core.DistkvMapInterface;

public abstract class DistkvConcepts<T> implements DistkvBaseOperation<T> {

  // The Reference of the key value map.
  protected DistkvMapInterface<String, Object> distkvKeyValueMap;

  protected DistkvConcepts(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    this.distkvKeyValueMap = distkvKeyValueMap;
  }

  @Override
  public void put(String key, T value) throws DistkvException {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    if (value != null) {
      distkvKeyValueMap.put(key, value);
    }
  }

  @Override
  public T get(String key) throws DistkvException {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    return (T) distkvKeyValueMap.get(key);
  }

  @Override
  public void drop(String key) throws DistkvException {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    distkvKeyValueMap.remove(key);
  }

}
