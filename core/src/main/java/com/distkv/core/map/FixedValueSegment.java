package com.distkv.core.map;

import com.distkv.core.block.Block;

import static com.google.common.base.Preconditions.checkArgument;

public class FixedValueSegment extends ValueSegment {

  private int fixedLength = -1;
  private int blockItemSize = -1;

  public FixedValueSegment(int initSize, int fixedLength) {
    super(initSize, fixedLength);

    if (fixedLength > 0) {
      this.fixedLength = fixedLength;
      this.blockItemSize = pool.getBlockSize() / fixedLength;
    }
  }

  protected byte[] getFixedValue(int key) {
    // TODO deal with overflow.
    Block block = getBlock(key);

    int offset = (key % blockItemSize) * fixedLength;
    return block.read(offset, fixedLength);
  }

  protected byte[] getFixedValues(int key, int cnt) {
    Block block = getBlock(key);

    int offset = (key % blockItemSize) * fixedLength;
    return block.read(offset, fixedLength * cnt);
  }

  protected void putFixedValue(int key, byte[] value) {
    Block block = getBlock(key);

    int offset = (key % blockItemSize) * fixedLength;
    block.write(offset, value);
  }

  private Block getBlock(int key) {
    checkArgument(fixedLength > 0);
    checkArgument(blockItemSize > 0);
    int blockIndex = key / blockItemSize;
    return blockArray[blockIndex];
  }

  public int getFixedValueCapacity() {
    return blockArray.length * pool.getBlockSize() / fixedLength;
  }

}
