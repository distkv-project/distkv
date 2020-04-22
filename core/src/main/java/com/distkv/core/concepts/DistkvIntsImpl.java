package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
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
    distkvKeyValueMap.put(key, new DistkvValue<>(TYPE.INT.ordinal(),value));
  }

  @Override
  public void incr(String key, int delta) {
    DistkvValue<Integer> integerDistkvValue = get(key);
    Integer oldValue = integerDistkvValue.getValue();
    oldValue += delta;
    integerDistkvValue.setValue(oldValue);
    super.distkvKeyValueMap.put(key, integerDistkvValue);
  }
}
