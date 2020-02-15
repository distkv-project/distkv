package com.distkv.core.map;

import com.distkv.common.utils.ByteUtil;

public class DoubleSegment extends FixedValueSegment {

  public DoubleSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_DOUBLE);
  }

  public double getValue(int key) {
    return getDouble(key);
  }

  public void putValue(int key, double value) {
    put(key, value);
  }
}
