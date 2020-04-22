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

  public abstract void put(String key, T value);

  /**
   * According to the key to return the corresponding value information.
   * @param key The key.
   * @return Value corresponding to the data type.
   */
  public T get(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    return as(distkvKeyValueMap.get(key));
  }

  /**
   * The specific return object is handed over to the specified subclass for implementation.
   */
  public abstract T as(DistkvValue<T> distkvValue);

  public Status drop(String key) {
    if (!distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    distkvKeyValueMap.remove(key);
    return Status.OK;
  }

}
