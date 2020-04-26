package com.distkv.core.struct.slist;

import com.distkv.common.entity.sortedList.SlistEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SlistLinkedImplTest {

  @Test
  public void testMain() {
    Slist slist = new SlistLinkedImpl();

    // For init
    List<SlistEntity> putList = new ArrayList<>();
    for (int i = 100; i < 120; i++) {
      putList.add(new SlistEntity(String.valueOf(i), i));
    }
    List<SlistEntity> anotherPutList = new ArrayList<>(putList);
    anotherPutList.add(new SlistEntity("101", 101));

    // Test put
    Assert.assertFalse(slist.put(anotherPutList));
    slist = new SlistLinkedImpl();
    Assert.assertTrue(slist.put(putList));

    // Test removeItem
    Assert.assertTrue(slist.removeItem("101"));
    Assert.assertFalse(slist.removeItem("123"));
    Assert.assertTrue(slist.removeItem("109"));

    // Test getSize
    Assert.assertEquals(slist.size(), 18);

    // Test putItem
    slist.putItem(new SlistEntity("109", 99));
    slist.putItem(new SlistEntity("119", 20));
    slist.putItem(new SlistEntity("115", -98));

    // Test getSize
    Assert.assertEquals(slist.size(), 19);

    // Test incrScore
    Assert.assertEquals(slist.incrScore("109", Integer.MAX_VALUE), -1);
    Assert.assertEquals(slist.incrScore("123", 123), 0);
    Assert.assertEquals(slist.incrScore("109", 1100), 1);

    // Test getItem
    Assert.assertEquals(slist.getItem("118").getFirst().intValue(), 118);
    Assert.assertEquals(slist.getItem("118").getSecond().intValue(), 2);
    Assert.assertEquals(slist.getItem("116").getFirst().intValue(), 116);
    Assert.assertEquals(slist.getItem("116").getSecond().intValue(), 4);
    Assert.assertEquals(slist.getItem("114").getFirst().intValue(), 114);
    Assert.assertEquals(slist.getItem("114").getSecond().intValue(), 5);

    // Test subList
    List<SlistEntity> listEntities = slist.subList(1, 5);
    Assert.assertEquals(listEntities.get(0).getMember(), "109");
    Assert.assertEquals(listEntities.get(0).getScore(), 1199);
    Assert.assertEquals(listEntities.get(1).getMember(), "118");
    Assert.assertEquals(listEntities.get(1).getScore(), 118);
    Assert.assertEquals(listEntities.get(2).getMember(), "117");
    Assert.assertEquals(listEntities.get(2).getScore(), 117);
    Assert.assertEquals(listEntities.get(3).getMember(), "116");
    Assert.assertEquals(listEntities.get(3).getScore(), 116);
    Assert.assertEquals(listEntities.get(4).getMember(), "114");
    Assert.assertEquals(listEntities.get(4).getScore(), 114);
  }

  /**
   * See the Test Case Issue on https://github.com/distkv-project/distkv/pull/487
   */
  @Test
  public void testPutItemByDeleteNode() {
    Slist slist = new SlistLinkedImpl();
    slist.putItem(new SlistEntity(String.valueOf(123), 123));
    slist.putItem(new SlistEntity(String.valueOf(123), 122));

    Assert.assertEquals(slist.getItem("123").getFirst().intValue(), 122);
    Assert.assertEquals(slist.getItem("123").getSecond().intValue(), 1);

    slist.putItem(new SlistEntity(String.valueOf(123), 124));

    Assert.assertEquals(slist.getItem("123").getFirst().intValue(), 124);
    Assert.assertEquals(slist.getItem("123").getSecond().intValue(), 1);
  }

}
