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
    NodeId id = NodeId.from(100, partitionId);
    Assert.assertEquals(id.getIndex(), 100);
    Assert.assertEquals(id.getPartitionId(), partitionId);
  }

  @Test
  public void testShardId() {
    ShardId id = ShardId.fromInt(31000);
    Assert.assertEquals(id.getIndex(), 31000);
  }

  @Test
  public void testPineHandleId() {
    PineHandleId id = PineHandleId.fromRandom();

    final String str = id.hex();
    Assert.assertEquals(str.length(),2 * PineHandleId.LENGTH);

    for (int i = 0; i < str.length(); i++) {
      Assert.assertTrue(isHexChar(str.charAt(i)));

    }
  }

  private boolean isHexChar(Character ch) {
    final boolean isLeft = (ch >= '0' && ch <= '9');
    final boolean isRight = (ch >= 'a' && ch <= 'f');
    return isLeft || isRight;
  }
}
