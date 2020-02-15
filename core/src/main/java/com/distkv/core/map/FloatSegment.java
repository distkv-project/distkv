package com.distkv.core.map;

import com.distkv.common.utils.ByteUtil;

public class FloatSegment extends FixedValueSegment {

  public FloatSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_FLOAT);
  }

  public float getValue(int key) {
    return getFloat(key);
  }

  public void putValue(int key, float value) {
    put(key, value);
  }
}
