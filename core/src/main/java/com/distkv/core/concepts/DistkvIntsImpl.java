package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.concepts.DistkvValue.TYPE;

public class DistkvIntsImpl extends DistkvConcepts<Integer> implements DistkvInts {

  public DistkvIntsImpl(DistkvMapInterface<String, DistkvValue> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void put(String key, Integer value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, new DistkvValue<>(TYPE.INT.ordinal(), value));
  }

  @Override
  public void incr(String key, int delta) {
    if (!super.distkvKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    DistkvValue<Integer> distkvValue = super.distkvKeyValueMap.get(key);
    distkvValue.setValue(distkvValue.getValue() + delta);
  }

  @Override
  public Integer as(DistkvValue<Integer> distkvValue) {
    return distkvValue.getValue();
  }
}
