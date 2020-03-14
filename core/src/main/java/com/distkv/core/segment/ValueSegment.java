package com.distkv.core.segment;

import com.distkv.core.block.Block;
import com.distkv.core.block.BlockPool;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class ValueSegment {

  protected BlockPool pool = BlockPool.getInstance();
  protected Block[] blockArray;
  protected int size;

  private AtomicBoolean isResizing = new AtomicBoolean(false);

  public ValueSegment(final int initSize) {
    checkArgument(initSize > 0);
    blockArray = new Block[initSize];
    for (int i = 0; i < initSize; i++) {
      blockArray[i] = pool.getBlock();
    }
  }

  protected void resize(final int size) {
    if (isResizing.compareAndSet(false, true)) {
      Block[] newBlock = new Block[size];
      System.arraycopy(blockArray, 0, newBlock, 0, blockArray.length);
      for (int i = blockArray.length; i < size; i++) {
        newBlock[i] = pool.getBlock();
      }
      blockArray = newBlock;
      isResizing.set(false);
    }
  }

  public int getSize() {
    return size;
  }

  /**
   * release the block to {@link BlockPool}.
   *
   * @param blockCnt the block count to be released.
   */
  public void releaseBlock(int blockCnt) {
    checkArgument(blockCnt < blockArray.length);
    int newBlockLength = blockArray.length - blockCnt;
    Block[] newBlockArray = new Block[newBlockLength];
    System.arraycopy(blockArray, blockCnt, newBlockArray, 0, newBlockLength);
    blockArray = newBlockArray;
  }


}
