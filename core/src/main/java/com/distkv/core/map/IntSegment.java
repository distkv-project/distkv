package com.distkv.core.map;

import com.distkv.common.utils.ByteUtil;

public class IntSegment extends FixedValueSegment {

  public IntSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_INT);
  }

  public int getValue(int key) {
    return getInt(key);
  }

  public void putValue(int key, int value) {
    put(key, value);
  }
}
