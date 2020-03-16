package com.distkv.core.segment;

import com.distkv.common.utils.ByteUtil;

public class ByteSegment extends FixedValueSegment {

  public ByteSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_BYTE);
  }

  public byte getValue(int key) {
    return getFixedValue(key)[0];
  }

  public byte[] getValues(int key, int cnt) {
    return getFixedValues(key, cnt);
  }

  public void putValue(int key, byte b) {
    putFixedValue(key, new byte[] {b});
  }

  public void putValues(int key, byte[] bytes) {
    putFixedValue(key, bytes);
  }
}
