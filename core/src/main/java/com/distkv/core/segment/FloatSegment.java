package com.distkv.core.segment;

import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

public class FloatSegment extends FixedValueSegment {

  public FloatSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_FLOAT);
  }

  protected float get(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readFloat(offset);
  }

  protected int put(int key, float value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }
}
