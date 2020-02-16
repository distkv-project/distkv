package com.distkv.core.block;

import sun.misc.Unsafe;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockPool implements Closeable {

  private static Unsafe getUnsafe() {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      return (Unsafe) field.get(null);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Unsafe unsafe = getUnsafe();
  // the number of block size is a multiple of page size.
  private static final int BLOCK_SIZE = unsafe.pageSize() << 8;
  private static final int INIT_BLOCK_NUMBER = 1 << 4; // 16
  private static final int BLOCK_NUMBER_UNIT = INIT_BLOCK_NUMBER; // 16
  private static final int LOW_WATER_MARK = INIT_BLOCK_NUMBER >> 1; // 8
  private static final int HIGH_WATER_MARK = INIT_BLOCK_NUMBER << 1; // 32

  private static BlockPool instance;
  private final int blockSize;
  private final ConcurrentLinkedQueue<Block> blocks = new ConcurrentLinkedQueue<>();

  private AtomicBoolean isAllocating = new AtomicBoolean(false);
  private AtomicBoolean isRecycling = new AtomicBoolean(false);

  public static BlockPool getInstance() {
    return getInstance(BLOCK_SIZE, INIT_BLOCK_NUMBER);
  }

  public static BlockPool getInstance(final int blockSize, final int initBlockNumber) {
    if (instance == null) {
      synchronized (BlockPool.class) {
        if (instance == null) {
          instance = new BlockPool(blockSize, initBlockNumber);
        }
      }
    }
    return instance;
  }

  private BlockPool(final int blockSize, final int initBlockNumber) {
    this.blockSize = blockSize;
    allocate(initBlockNumber);
  }

  public Block getBlock() {
    Block block;
    while (true) {
      block = blocks.poll();
      allocateIfNeeded();
      if (block != null) {
        return block;
      }
    }
  }

  public Block[] getBlock(int blockNumber) {
    Block[] blockArray = new Block[blockNumber];
    for (int i = 0; i < blockNumber; i++) {
      blockArray[i] = getBlock();
    }
    return blockArray;
  }

  public void returnBlock(Block block) {
    blocks.add(block);
    recycleIfNeeded();
  }

  public void returnBlocks(Block[] blockArray) {
    for (Block block : blockArray) {
      returnBlock(block);
    }
  }

  private void allocateIfNeeded() {
    if (blocks.size() < LOW_WATER_MARK &&
        isAllocating.compareAndSet(false, true)) {
      CompletableFuture.runAsync(() -> allocate(BLOCK_NUMBER_UNIT))
          .thenRunAsync(() -> isAllocating.set(false));
    }
  }

  private void allocate(int initBlockNumber) {
    for (int i = 0; i < initBlockNumber; i++) {
      blocks.add(new Block(this.blockSize));
    }
  }

  private void recycleIfNeeded() {
    if (blocks.size() > HIGH_WATER_MARK &&
        isRecycling.compareAndSet(false, true)) {
      CompletableFuture.runAsync(() -> {
        while (blocks.size() > BLOCK_NUMBER_UNIT) {
          Block block = blocks.poll();
          if (block != null) {
            block.clean();
          }
        }
      }).thenRunAsync(() -> isRecycling.set(false));
    }
  }

  public int getBlockSize() {
    return blockSize;
  }

  @Override
  public void close() {
    Block block;
    while ((block = blocks.poll()) != null) {
      block.clean();
    }
  }
}
