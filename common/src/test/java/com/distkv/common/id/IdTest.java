package com.distkv.common.id;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IdTest {

  @Test
  public void testPartitionId() {
    PartitionId id = PartitionId.fromShort((short) 13);
    Assert.assertEquals(id.getIndex(), (short) 13);
  }

  @Test
  public void testNodeId() {
    final PartitionId partitionId = PartitionId.fromShort((short) 21);
    NodeId id = NodeId.from(100, partitionId, true);
    Assert.assertEquals(id.getIndex(), 100);
    Assert.assertEquals(id.getPartitionId(), partitionId);
    Assert.assertTrue(id.isMaster());
  }

  @Test
  public void testShardId() {
    ShardId id = ShardId.fromInt(31000);
    Assert.assertEquals(id.getIndex(), 31000);
  }

  @Test
  public void testPineHandleId() {
    PineHandleId id = PineHandleId.fromRandom();
    char[] chars= id.hex().toCharArray();
    Assert.assertEquals(32,id.hex().length());
    for(char i:chars) {
      Assert.assertFalse(i >= 'A' && i <= 'Z');
    }
  }
}
