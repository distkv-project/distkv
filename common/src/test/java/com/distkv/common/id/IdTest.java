package com.distkv.common.id;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IdTest {

  @Test
  public void testGroupId() {
    GroupId id = GroupId.fromIndex((short) 13);
    Assert.assertEquals(id.getIndex(), (short) 13);
  }

  @Test
  public void testNodeId() {
    final GroupId groupId = GroupId.fromIndex((short) 21);
    NodeId id = NodeId.from(100, groupId);
    Assert.assertEquals(id.getIndex(), 100);
    Assert.assertEquals(id.getGroupId(), groupId);
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
