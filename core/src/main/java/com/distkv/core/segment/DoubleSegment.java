package com.distkv.core.segment;

import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

public class DoubleSegment extends FixedValueSegment {

  public DoubleSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_DOUBLE);
  }

  protected double get(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readDouble(offset);
  }

  protected int put(int key, double value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }
}
