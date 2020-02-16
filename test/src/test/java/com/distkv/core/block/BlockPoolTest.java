package com.distkv.core.block;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BlockPoolTest {

  @Test
  public void testAllocate() {
    try (BlockPool blockPool = BlockPool.getInstance()) {
      int blockCnt = 33;
      Block[] blocks = blockPool.getBlock(blockCnt);
      assertEquals(blocks.length, blockCnt);
      blockPool.returnBlocks(blocks);
    }
  }
}
