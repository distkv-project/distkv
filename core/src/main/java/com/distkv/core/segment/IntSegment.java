package com.distkv.core.segment;

import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

public class IntSegment extends FixedValueSegment {

  public IntSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_INT);
  }

  public int get(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readInt(offset);
  }

  public int put(int key, int value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }

  public int put(int value) {
    Block block = getBlock(size);
    int offset = size % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }
}
