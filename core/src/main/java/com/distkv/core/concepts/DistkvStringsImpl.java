package com.distkv.core.concepts;


import com.distkv.core.DistkvMapInterface;

public class DistkvStringsImpl extends DistkvConcepts<String> implements DistkvStrings {

  public DistkvStringsImpl(DistkvMapInterface<String, DistkvValue<String>> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }
}
