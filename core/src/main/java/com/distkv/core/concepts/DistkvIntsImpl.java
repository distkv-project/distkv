package com.distkv.core.concepts;

import com.distkv.core.DistkvMapInterface;

public class DistkvIntsImpl
    extends DistkvConcepts<Integer>
    implements DistkvInts {

  public DistkvIntsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void incr(String key, int delta) {
    Integer oldValue = get(key);
    oldValue += delta;
    super.distkvKeyValueMap.put(key, oldValue);
  }
}
