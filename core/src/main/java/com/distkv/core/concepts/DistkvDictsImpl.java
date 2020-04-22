package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.core.DistkvMapInterface;

import com.distkv.core.concepts.DistkvValue.TYPE;
import java.util.Map;

public class DistkvDictsImpl extends DistkvConcepts<Map<String, String>> implements DistkvDicts {

  public DistkvDictsImpl(
      DistkvMapInterface<String, DistkvValue> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public Map<String, String> as(DistkvValue<Map<String, String>> distkvValue) {
    return distkvValue.getValue();
  }

  @Override
  public void put(String key, Map<String, String> value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, new DistkvValue<>(TYPE.DICT.ordinal(),value));
  }
}
