package com.distkv.core.concepts;

import com.distkv.core.DistkvMapInterface;

public class DistkvIntsImpl extends DistkvConcepts<Integer> implements DistkvInts {

  public DistkvIntsImpl(DistkvMapInterface<String, DistkvValue<Integer>> distkvKeyValueMap) {
    super(distkvKeyValueMap);
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
