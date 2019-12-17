package com.distkv.dst.core.operatorImpl;

import com.distkv.dst.core.operatorset.DstConcepts;
import com.distkv.dst.core.operatorset.DstSets;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import java.util.Set;

public class DstSetsImpl extends DstConcepts<Set<String>> implements DstSets {

  public DstSetsImpl() {
  }

  @Override
  public void putItem(String key, String itemValue) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    dstKeyValueMap.get(key).add(itemValue);
  }

  @Override
  public Status removeItem(String key, String itemValue) {
    if (!dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    dstKeyValueMap.get(key).remove(itemValue);
    return Status.OK;
  }

  @Override
  public boolean exists(String key, String value) throws KeyNotFoundException {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }

    return dstKeyValueMap.get(key).contains(value);
  }

}
