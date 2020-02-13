package com.distkv.core.map;

import com.distkv.core.block.Block;
import com.distkv.core.block.BlockPool;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ValueSegment {

  protected BlockPool pool = BlockPool.getInstance();
  protected Block[] blockArray;

  private AtomicBoolean isResizing = new AtomicBoolean(false);

  public ValueSegment(final int initSize, final int fixedLength) {
    blockArray = new Block[initSize];
    for (int i = 0; i < initSize; i++) {
      blockArray[i] = pool.getBlock();
    }
  }


  public void resize(final int size) {
    if (isResizing.compareAndSet(false, true)) {
      Block[] newBlock = new Block[size];
      System.arraycopy(blockArray, 0, newBlock, 0, blockArray.length);
      blockArray = newBlock;
      isResizing.set(false);
    }
  }
}
