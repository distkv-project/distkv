package com.distkv.core.segment;

import com.distkv.common.utils.ByteUtil;
import com.distkv.core.block.Block;

import static com.google.common.base.Preconditions.checkArgument;

public class NonFixedSegment extends AbstractNonFixedSegment {

  public NonFixedSegment(int initSize) {
    super(initSize);
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
      blockValueCntArray[blockIndex] = size - 1;
      return addValue(value);
    }
  }

  public int addKeyValue(byte[] key, byte[] value) {
    checkArgument(key.length + value.length + ByteUtil.SIZE_OF_LONG <= pool.getBlockSize());
    Block block = blockArray[blockIndex];
    if (block.writeTwoNonFixedValue(key, value) > 0) {
      int pointer = size;
      size = size + 2;
      return pointer;
    } else {
      blockIndex++;
      resize(blockIndex + 1);
      blockValueCntArray[blockIndex] = size - 1;
      return addKeyValue(key, value);
    }
  }

  public byte[] getValue(int pointer) {
    checkArgument(pointer < size);
    Block block = blockArray[locateBlock(pointer)];
    pointer = pointer - blockValueCntArray[blockIndex];
    return block.readNonFixedValue(pointer);
  }

  public byte[][] getKeyValue(int pointer) {
    checkArgument(pointer < (size << 1));
    int blockIndex = locateBlock(pointer);
    Block block = blockArray[blockIndex];
    pointer = pointer - blockValueCntArray[blockIndex];
    return block.readTwoNonFixedValues(pointer);
  }
}
