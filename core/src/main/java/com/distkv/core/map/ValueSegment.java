package com.distkv.core.map;

import com.distkv.core.block.Block;
import com.distkv.core.block.BlockPool;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ValueSegment {

  private BlockPool pool = BlockPool.getInstance();
  private Block[] blockArray;
  private int fixedLength = -1;
  private int blockItemSize = -1;

  private AtomicBoolean isResizing = new AtomicBoolean(false);

  public ValueSegment(final int initSize, final int fixedLength) {
    blockArray = new Block[initSize];
    for (int i = 0; i < initSize; i++) {
      blockArray[i] = pool.getBlock();
    }

    if (fixedLength > 0) {
      this.fixedLength = fixedLength;
      this.blockItemSize = pool.getBlockSize() % fixedLength;
    }
  }

  protected byte[] getFixedValue(int key) {
    Block block = getBlock(key);

    int offset = (key % blockItemSize) * fixedLength;
    return block.read(offset, fixedLength);
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

  public void resize(final int size) {
    if (isResizing.compareAndSet(false, true)) {
      Block[] newBlock = new Block[size];
      System.arraycopy(blockArray, 0, newBlock, 0, blockArray.length);
      blockArray = newBlock;
      isResizing.set(false);
    }
  }
}
