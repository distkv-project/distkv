package com.distkv.core.segment;

public abstract class AbstractNonFixedSegment extends ValueSegment {

  protected int[] blockValueCntArray;
  protected int blockIndex;

  public AbstractNonFixedSegment(int initSize) {
    super(initSize);
    blockValueCntArray = new int[initSize];
  }

  public int locateBlock(int pointer) {
    return binarySearch(pointer, 0, blockValueCntArray.length - 1);
  }

  public int binarySearch(int value, int start, int end) {
    if (end - start <= 2) {
      if (start + 1 <= end && blockValueCntArray[start + 1] >= value) {
        return start;
      } else if (start + 2 <= end && blockValueCntArray[start + 2] >= value) {
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

  @Override
  public void releaseBlock(int blockCnt) {
    super.releaseBlock(blockCnt);
    int[] newBlockValueCntArray = new int[blockValueCntArray.length - blockCnt];
    System.arraycopy(blockValueCntArray, blockCnt,
        newBlockValueCntArray, 0, newBlockValueCntArray.length);
    blockValueCntArray = newBlockValueCntArray;
  }
}
