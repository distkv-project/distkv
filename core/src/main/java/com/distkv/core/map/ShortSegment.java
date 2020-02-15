package com.distkv.core.map;

import com.distkv.common.utils.ByteUtil;

public class ShortSegment extends FixedValueSegment {

  public ShortSegment(int initSize, int fixedLength) {
    super(initSize, ByteUtil.SIZE_OF_SHORT);
  }

  public short getValue(int key) {
    return getShort(key);
  }

  public void putValue(int key, short value) {
    put(key, value);
  }
}
