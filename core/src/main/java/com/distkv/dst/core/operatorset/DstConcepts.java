package com.distkv.dst.core.operatorset;

import com.distkv.dst.common.utils.Status;
import com.distkv.dst.core.DstMapInterface;

public abstract class DstConcepts<T> {

  protected DstMapInterface<String, T> dstKeyValueMap;

  public Status drop(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }

    dstKeyValueMap.remove(key);
    return Status.OK;
  }

}
