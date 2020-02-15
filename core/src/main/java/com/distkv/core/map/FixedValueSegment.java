package com.distkv.core.map;

import com.distkv.core.block.Block;

import static com.google.common.base.Preconditions.checkArgument;

public class FixedValueSegment extends ValueSegment {

  private int fixedLength = -1;
  private int blockItemSize = -1;

  public FixedValueSegment(int initSize, int fixedLength) {
    super(initSize);

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

  protected short getShort(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readShort(offset);
  }

  protected int getInt(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readInt(offset);
  }

  protected long getLong(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readLong(offset);
  }

  protected double getDouble(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readDouble(offset);
  }

  protected float getFloat(int key) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    return block.readFloat(offset);
  }

  protected byte[] getFixedValues(int key, int cnt) {
    Block block = getBlock(key);

    int offset = (key % blockItemSize) * fixedLength;
    return block.read(offset, fixedLength * cnt);
  }

  protected int put(int key, short value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }

  protected int put(int key, int value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }

  protected int put(int key, long value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }

  protected int put(int key, double value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
  }

  protected int put(int key, float value) {
    Block block = getBlock(key);
    int offset = key % blockItemSize;
    block.write(offset, value);
    return getAndAddPointer();
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

  private Block getBlock(int key) {
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

}
