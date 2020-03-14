package com.distkv.core.segment;

import com.distkv.core.block.Block;

import static com.google.common.base.Preconditions.checkArgument;

public class FixedValueSegment extends ValueSegment {

  private int fixedLength;
  protected int blockItemSize;

  public FixedValueSegment(int initSize, int fixedLength) {
    super(initSize);

    checkArgument(fixedLength > 0);
    this.fixedLength = fixedLength;
    this.blockItemSize = pool.getBlockSize() / fixedLength;
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

  protected int putFixedValue(int key, byte[] value) {
    Block block = getBlock(key);

    int offset = (key % blockItemSize) * fixedLength;
    block.write(offset, value);
    return getAndAddPointer();
  }

  protected int getAndAddPointer() {
    int pointer = size;
    size++;
    return pointer;
  }

  protected Block getBlock(int key) {
    checkArgument(fixedLength > 0);
    checkArgument(blockItemSize > 0);
    int blockIndex = key / blockItemSize;
    if (blockIndex >= blockArray.length) {
      resize(blockIndex + 1);
    }
    return blockArray[blockIndex];
  }

  public int getFixedValueCapacity() {
    return blockArray.length * pool.getBlockSize() / fixedLength;
  }

  @Override
  public void releaseBlock(int blockCnt) {
    super.releaseBlock(blockCnt);
    size -= blockCnt * blockItemSize;
  }
}
