package com.distkv.common.id;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
    String[] s = new String[]{"0","1","2","3","4","5","6","7",
                              "8","9","a","b","c","d","e","f",
                              "g","h","i","j","k","l","m","n",
                              "o","p","q","r","s","t","u","v","w","x","y","z"};
    Set<Character> set = new HashSet<>();

    Arrays.asList(s).stream().forEach(x -> {
      set.add(x.charAt(0));
    });

    PineHandleId id = PineHandleId.fromRandom();

    String str = id.hex();
    Assert.assertEquals(str.length(),32);

    for (int i = 0; i < str.length(); i++) {
      Assert.assertFalse(set.contains(str.indexOf(i)));
    }
  }
}
