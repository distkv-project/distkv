package com.distkv.core.struct.slist;

import com.distkv.common.entity.sortedList.SortedListEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SortedListLinkedImplTest {

  @Test
  public void testMain() {
    SortedList sortedList = new SortedListLinkedImpl();

    // For init
    List<SortedListEntity> putList = new ArrayList<>();
    for (int i = 100; i < 120; i++) {
      putList.add(new SortedListEntity(String.valueOf(i), i));
    }
    List<SortedListEntity> anotherPutList = new ArrayList<>(putList);
    anotherPutList.add(new SortedListEntity("101", 101));

    // Test put
    Assert.assertFalse(sortedList.put(anotherPutList));
    sortedList = new SortedListLinkedImpl();
    Assert.assertTrue(sortedList.put(putList));

    // Test removeItem
    Assert.assertTrue(sortedList.removeItem("101"));
    Assert.assertFalse(sortedList.removeItem("123"));
    Assert.assertTrue(sortedList.removeItem("109"));

    // Test getSize
    Assert.assertEquals(sortedList.size(), 18);

    // Test putItem
    sortedList.putItem(new SortedListEntity("109", 99));
    sortedList.putItem(new SortedListEntity("119", 20));
    sortedList.putItem(new SortedListEntity("115", -98));

    // Test getSize
    Assert.assertEquals(sortedList.size(), 19);

    // Test incrScore
    Assert.assertEquals(sortedList.incrScore("109", Integer.MAX_VALUE), -1);
    Assert.assertEquals(sortedList.incrScore("123", 123), 0);
    Assert.assertEquals(sortedList.incrScore("109", 1100), 1);

    // Test getItem
    Assert.assertEquals(sortedList.getItem("118").getFirst().intValue(), 118);
    Assert.assertEquals(sortedList.getItem("118").getSecond().intValue(), 2);
    Assert.assertEquals(sortedList.getItem("116").getFirst().intValue(), 116);
    Assert.assertEquals(sortedList.getItem("116").getSecond().intValue(), 4);
    Assert.assertEquals(sortedList.getItem("114").getFirst().intValue(), 114);
    Assert.assertEquals(sortedList.getItem("114").getSecond().intValue(), 5);

    // Test subList
    List<SortedListEntity> listEntities = sortedList.subList(1, 5);
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
    SortedList slist = new SortedListLinkedImpl();
    slist.putItem(new SortedListEntity(String.valueOf(123), 123));
    slist.putItem(new SortedListEntity(String.valueOf(123), 122));

    Assert.assertEquals(slist.getItem("123").getFirst().intValue(), 122);
    Assert.assertEquals(slist.getItem("123").getSecond().intValue(), 1);

    slist.putItem(new SortedListEntity(String.valueOf(123), 124));

    Assert.assertEquals(slist.getItem("123").getFirst().intValue(), 124);
    Assert.assertEquals(slist.getItem("123").getSecond().intValue(), 1);
  }

}
