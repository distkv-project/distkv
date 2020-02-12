package com.distkv.core.map;


import com.distkv.common.utils.ByteUtil;

public class LongSegment extends ValueSegment {

  public LongSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_LONG);
  }

  public long getValue(int key) {
    return ByteUtil.getLong(getFixedValue(key));
  }

  public void putValue(int key, long value) {
    putFixedValue(key, ByteUtil.toArray(value));
  }

}
