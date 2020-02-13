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

  public void addValue(byte[] value) {
    checkArgument(value.length + ByteUtil.SIZE_OF_INT <= pool.getBlockSize());
    Block block = blockArray[blockIndex];
    if (block.addNonFixedValue(value) > 0) {
      size++;
      blockValueCntArray[blockIndex] = size;
    } else {
      resize(blockIndex + 2);
      addValue(value);
    }
  }

  public void addKeyValue(byte[] key, byte[] value) {

  }

  public void getValue(int pointer) {

  }

  public void getKeyValue(int pointer) {

  }

  public void resize(final int newSize) {
    super.resize(newSize);
    int[] newBlockValueCntArray = new int[newSize];
    System.arraycopy(blockValueCntArray, 0, newBlockValueCntArray, 0, blockValueCntArray.length);
    blockValueCntArray = newBlockValueCntArray;
  }
}
