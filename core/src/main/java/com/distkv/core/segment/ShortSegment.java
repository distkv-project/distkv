package com.distkv.core.segment;

import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

public class ShortSegment extends FixedValueSegment {

  public ShortSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_SHORT);
  }

  protected short get(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readShort(offset);
  }

  protected int put(int key, short value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }
}
