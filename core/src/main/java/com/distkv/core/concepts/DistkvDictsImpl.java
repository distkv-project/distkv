package com.distkv.core.concepts;

import com.distkv.core.DistkvMapInterface;

import java.util.Map;

public class DistkvDictsImpl extends DistkvConcepts<Map<String, String>> implements DistkvDicts {

  public DistkvDictsImpl(
      DistkvMapInterface<String, DistkvValue<Map<String, String>>> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }
}
