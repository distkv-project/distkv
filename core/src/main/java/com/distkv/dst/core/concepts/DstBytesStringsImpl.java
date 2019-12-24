package com.distkv.dst.core.concepts;

import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import com.distkv.dst.core.DstHashMapImpl;
import com.distkv.dst.core.DstMapInterface;

public class DstBytesStringsImpl extends DstConcepts<String> implements DstStrings {

  protected DstMapInterface<String, byte[]> dstKeyValueMap = new DstHashMapImpl<>();

  @Override
  public void put(String key, String value) {
    dstKeyValueMap.put(key, value.getBytes());
  }

  @Override
  public String get(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      throw new KeyNotFoundException(key);
    }
    return new String(dstKeyValueMap.get(key));
  }

  @Override
  public Status drop(String key) {
    if (!dstKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    dstKeyValueMap.remove(key);
    return Status.OK;
  }
}
