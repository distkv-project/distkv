package com.distkv.core.concepts;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SetItemNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;

import java.util.Set;

public class DistkvSetsImpl extends DistkvConcepts<Set<String>> implements DistkvSets {

  public DistkvSetsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void putItem(String key, String itemValue) {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    get(key).add(itemValue);
  }

  @Override
  public Status removeItem(String key, String itemValue) {
    if (!distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    if (!get(key).contains(itemValue)) {
      throw new SetItemNotFoundException(key, itemValue);
    }
    get(key).remove(itemValue);
    return Status.OK;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return get(key).contains(value);
  }

}
