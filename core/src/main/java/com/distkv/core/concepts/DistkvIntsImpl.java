package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.concepts.DistkvValue.TYPE;

public class DistkvIntsImpl extends DistkvConcepts<Integer> implements DistkvInts {

  public DistkvIntsImpl(DistkvMapInterface<String, DistkvValue<Integer>> distkvKeyValueMap) {
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
    Integer value = distkvValue.getValue();
    value += delta;
    distkvValue.setValue(value);
  }

  @Override
  public Integer as(DistkvValue<Integer> distkvValue) {
    return distkvValue.getValue();
  }
}
