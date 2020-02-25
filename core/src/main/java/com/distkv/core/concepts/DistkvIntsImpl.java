package com.distkv.core.concepts;

public class DistkvIntsImpl
    extends DistkvConcepts<Integer>
    implements DistkvInts {

  @Override
  public void incr(String key, int delta) {
    Integer oldValue = super.distkvKeyValueMap.get(key);
    oldValue += delta;
    super.distkvKeyValueMap.put(key, oldValue);
  }
}
