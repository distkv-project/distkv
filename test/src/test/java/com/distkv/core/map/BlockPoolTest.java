package com.distkv.core.map;

import com.distkv.core.block.Block;
import com.distkv.core.block.BlockPool;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BlockPoolTest {

  private BlockPool blockPool = BlockPool.getInstance();

  @Test
  public void testAllocate() {
    int blockCnt = 33;
    Block[] blocks = blockPool.getBlock(blockCnt);
    assertEquals(blocks.length, blockCnt);
    blockPool.returnBlocks(blocks);
  }
}
