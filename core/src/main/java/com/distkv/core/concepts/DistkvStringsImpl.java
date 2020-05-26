package com.distkv.core.concepts;


import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.core.concepts.DistkvValue.TYPE;

public class DistkvStringsImpl extends DistkvConcepts<String> implements DistkvStrings {

  public DistkvStringsImpl(DistkvMapInterface<String, DistkvValue> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void put(String key, String value) {
    if (distkvKeyValueMap.containsKey(key)) {
      throw new DistkvKeyDuplicatedException(key);
    }
    distkvKeyValueMap.put(key, new DistkvValue<>(TYPE.STRING.ordinal(),value));
  }

  @Override
  public String as(DistkvValue<String> distkvValue) {
    return distkvValue.getValue();
  }
}
