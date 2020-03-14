package com.distkv.core.segment;


import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

public class LongSegment extends FixedValueSegment {

  public LongSegment(int initSize) {
    super(initSize, ByteUtil.SIZE_OF_LONG);
  }

  protected long get(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readLong(offset);
  }

  protected int put(int key, long value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }

}
