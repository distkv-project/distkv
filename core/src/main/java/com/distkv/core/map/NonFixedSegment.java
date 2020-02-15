package com.distkv.core.map;

import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

import static com.google.common.base.Preconditions.checkArgument;

public class NonFixedSegment extends ValueSegment {

  private int[] blockValueCntArray;
  private int blockIndex;
  private int size;

  public NonFixedSegment(int initSize) {
    super(initSize, -1);
    blockValueCntArray = new int[initSize];
  }

  public int addValue(byte[] value) {
    checkArgument(value.length + ByteUtil.SIZE_OF_INT <= pool.getBlockSize());
    Block block = blockArray[blockIndex];
    if (block.writeNonFixedValue(value) > 0) {
      int pointer = size;
      size++;
      return pointer;
    } else {
      blockIndex++;
      resize(blockIndex + 1);
      blockValueCntArray[blockIndex] = size;
      return addValue(value);
    }
  }

  public int addKeyValue(byte[] key, byte[] value) {
    checkArgument(key.length + value.length <= pool.getBlockSize());
    Block block = blockArray[blockIndex];
    if (block.writeTwoNonFixedValue(key, value) > 0) {
      int pointer = size;
      size = size + 2;
      return pointer;
    } else {
      blockIndex++;
      resize(blockIndex + 1);
      blockValueCntArray[blockIndex] = size;
      return addKeyValue(key, value);
    }
  }

  public byte[] getValue(int pointer) {
    checkArgument(pointer < size);
    Block block = blockArray[locateBlock(pointer)];
    return block.readNonFixedValue(pointer);
  }

  public byte[][] getKeyValue(int pointer) {
    Block block = blockArray[locateBlock(pointer)];
    return block.readTwoNonFixedValues(pointer);
  }

  public int locateBlock(int pointer) {
    return binarySearch(pointer, 0, blockValueCntArray.length - 1);
  }

  public int binarySearch(int value, int start, int end) {
    if (end - start <= 2) {
      if (start + 1 <= end && blockValueCntArray[start + 1] > value) {
        return start;
      } else if (start + 2 <= end && blockValueCntArray[start + 2] > value) {
        return start + 1;
      } else {
        return end;
      }
    } else {
      int mid = (start + end) / 2;
      if (value >= blockValueCntArray[mid]) {
        return binarySearch(value, mid, end);
      } else {
        return binarySearch(value, start, mid);
      }
    }
  }

  public void resize(final int newSize) {
    super.resize(newSize);
    int[] newBlockValueCntArray = new int[newSize];
    System.arraycopy(blockValueCntArray, 0, newBlockValueCntArray, 0, blockValueCntArray.length);
    blockValueCntArray = newBlockValueCntArray;
  }
}
