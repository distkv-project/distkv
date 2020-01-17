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
    Assert.assertEquals(sortedList.put(anotherPutList), false);
    sortedList = new SortedListLinkedImpl();
    Assert.assertEquals(sortedList.put(putList), true);

    // Test removeItem
    Assert.assertEquals(sortedList.removeItem("101"), true);
    Assert.assertEquals(sortedList.removeItem("123"), false);
    Assert.assertEquals(sortedList.removeItem("109"), true);

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
    Assert.assertEquals(listEntities.get(0).getMember().equals("109"), true);
    Assert.assertEquals(listEntities.get(0).getScore(), 1199);
    Assert.assertEquals(listEntities.get(1).getMember().equals("118"), true);
    Assert.assertEquals(listEntities.get(1).getScore(), 118);
    Assert.assertEquals(listEntities.get(2).getMember().equals("117"), true);
    Assert.assertEquals(listEntities.get(2).getScore(), 117);
    Assert.assertEquals(listEntities.get(3).getMember().equals("116"), true);
    Assert.assertEquals(listEntities.get(3).getScore(), 116);
    Assert.assertEquals(listEntities.get(4).getMember().equals("114"), true);
    Assert.assertEquals(listEntities.get(4).getScore(), 114);
  }

}
