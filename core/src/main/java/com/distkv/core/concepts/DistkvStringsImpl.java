package com.distkv.core.concepts;


import com.distkv.core.DistkvMapInterface;

public class DistkvStringsImpl extends DistkvConcepts<String> implements DistkvStrings {

  public DistkvStringsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }
}
