package com.distkv.core.struct.slist;

import com.distkv.common.entity.sortedList.SortedListEntity;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SortedListImplTest {

  @Test
  public static void testMain() {
    testSortedListImpl(new SortedListRBTreeImpl());
  }

  public static void testSortedListImpl(SortedList sortedList) {
    List<SortedListEntity> entities = new ArrayList<>();
    entities.add(new SortedListEntity(String.valueOf(5), 5));
    entities.add(new SortedListEntity(String.valueOf(9), 9));
    entities.add(new SortedListEntity(String.valueOf(99), 9));
    entities.add(new SortedListEntity(String.valueOf(7), 7));
    entities.add(new SortedListEntity(String.valueOf(8), 8));
    entities.add(new SortedListEntity(String.valueOf(88), 8));
    entities.add(new SortedListEntity(String.valueOf(888), 8));

    // To test put method
    List<SortedListEntity> anothers = new ArrayList<>(entities);
    anothers.add(new SortedListEntity(String.valueOf(7), 77));
    // The list anothers has duplicated members, put failure
    Assert.assertFalse(sortedList.put(anothers));
    // Put the list entities success
    Assert.assertTrue(sortedList.put(entities));

    // To test size method
    Assert.assertEquals(sortedList.size(), 7);

    // To test getItem method
    Assert.assertEquals(sortedList.getItem("5").getFirst().intValue(), 5);
    Assert.assertEquals(sortedList.getItem("5").getSecond().intValue(), 7);
    Assert.assertEquals(sortedList.getItem("9").getFirst().intValue(), 9);
    Assert.assertEquals(sortedList.getItem("9").getSecond().intValue(), 1);
    Assert.assertEquals(sortedList.getItem("99").getFirst().intValue(), 9);
    Assert.assertEquals(sortedList.getItem("99").getSecond().intValue(), 1);
    Assert.assertEquals(sortedList.getItem("7").getFirst().intValue(), 7);
    Assert.assertEquals(sortedList.getItem("7").getSecond().intValue(), 6);
    Assert.assertEquals(sortedList.getItem("8").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("8").getSecond().intValue(), 3);
    Assert.assertEquals(sortedList.getItem("88").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("88").getSecond().intValue(), 3);
    Assert.assertEquals(sortedList.getItem("888").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("888").getSecond().intValue(), 3);
    Assert.assertNull(sortedList.getItem("1234"));

    // To test subList method
    List<SortedListEntity> topList = sortedList.subList(1, 5);
    Assert.assertEquals(topList.size(), 5);
    Assert.assertEquals(topList.get(0).getMember(), "9");
    Assert.assertEquals(topList.get(0).getScore(), 9);
    Assert.assertEquals(topList.get(1).getMember(), "99");
    Assert.assertEquals(topList.get(1).getScore(), 9);
    Assert.assertEquals(topList.get(2).getMember(), "8");
    Assert.assertEquals(topList.get(2).getScore(), 8);
    Assert.assertEquals(topList.get(3).getMember(), "88");
    Assert.assertEquals(topList.get(3).getScore(), 8);
    Assert.assertEquals(topList.get(4).getMember(), "888");
    Assert.assertEquals(topList.get(4).getScore(), 8);
    topList = sortedList.subList(3, 3);
    Assert.assertEquals(topList.size(), 3);
    Assert.assertEquals(topList.get(0).getMember(), "8");
    Assert.assertEquals(topList.get(0).getScore(), 8);
    Assert.assertEquals(topList.get(1).getMember(), "88");
    Assert.assertEquals(topList.get(1).getScore(), 8);
    Assert.assertEquals(topList.get(2).getMember(), "888");
    Assert.assertEquals(topList.get(2).getScore(), 8);

    // To test putItem method
    sortedList.putItem(new SortedListEntity(String.valueOf(2), 2));
    Assert.assertEquals(sortedList.size(), 8);
    Assert.assertEquals(sortedList.getItem("2").getFirst().intValue(), 2);
    Assert.assertEquals(sortedList.getItem("2").getSecond().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("8").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("8").getSecond().intValue(), 3);
    sortedList.putItem(new SortedListEntity(String.valueOf(444), 4));
    Assert.assertEquals(sortedList.size(), 9);
    Assert.assertEquals(sortedList.getItem("2").getFirst().intValue(), 2);
    Assert.assertEquals(sortedList.getItem("2").getSecond().intValue(), 9);
    Assert.assertEquals(sortedList.getItem("8").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("8").getSecond().intValue(), 3);
    Assert.assertEquals(sortedList.getItem("444").getFirst().intValue(), 4);
    Assert.assertEquals(sortedList.getItem("444").getSecond().intValue(), 8);
    sortedList.putItem(new SortedListEntity(String.valueOf(10), 10));
    Assert.assertEquals(sortedList.size(), 10);
    Assert.assertEquals(sortedList.getItem("10").getFirst().intValue(), 10);
    Assert.assertEquals(sortedList.getItem("10").getSecond().intValue(), 1);
    Assert.assertEquals(sortedList.getItem("8").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("8").getSecond().intValue(), 4);

    // To test removeItem method
    Assert.assertFalse(sortedList.removeItem("333"));
    Assert.assertTrue(sortedList.removeItem("88"));
    Assert.assertEquals(sortedList.size(), 9);
    Assert.assertNull(sortedList.getItem("88"));
    Assert.assertEquals(sortedList.getItem("7").getFirst().intValue(), 7);
    Assert.assertEquals(sortedList.getItem("7").getSecond().intValue(), 6);
    Assert.assertEquals(sortedList.getItem("888").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("888").getSecond().intValue(), 4);
    Assert.assertTrue(sortedList.removeItem("2"));
    Assert.assertEquals(sortedList.size(), 8);
    Assert.assertNull(sortedList.getItem("2"));
    Assert.assertEquals(sortedList.getItem("7").getFirst().intValue(), 7);
    Assert.assertEquals(sortedList.getItem("7").getSecond().intValue(), 6);
    Assert.assertEquals(sortedList.getItem("888").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("888").getSecond().intValue(), 4);

    // To test incrScore method
    Assert.assertEquals(sortedList.incrScore("99", Integer.MAX_VALUE), -1);
    sortedList.putItem(new SortedListEntity(String.valueOf(-1), -1));
    Assert.assertEquals(sortedList.incrScore("-1", Integer.MIN_VALUE), -1);
    Assert.assertEquals(sortedList.incrScore("123456", 123456), 0);
    Assert.assertEquals(sortedList.incrScore("99", -4), 1);
    Assert.assertEquals(sortedList.size(), 9);
    Assert.assertEquals(sortedList.getItem("9").getFirst().intValue(), 9);
    Assert.assertEquals(sortedList.getItem("9").getSecond().intValue(), 2);
    Assert.assertEquals(sortedList.getItem("5").getFirst().intValue(), 5);
    Assert.assertEquals(sortedList.getItem("5").getSecond().intValue(), 6);
    Assert.assertEquals(sortedList.getItem("99").getFirst().intValue(), 5);
    Assert.assertEquals(sortedList.getItem("99").getSecond().intValue(), 6);
    Assert.assertEquals(sortedList.getItem("-1").getFirst().intValue(), -1);
    Assert.assertEquals(sortedList.getItem("-1").getSecond().intValue(), 9);
    Assert.assertEquals(sortedList.incrScore("7", 3), 1);
    Assert.assertEquals(sortedList.size(), 9);
    Assert.assertEquals(sortedList.getItem("10").getFirst().intValue(), 10);
    Assert.assertEquals(sortedList.getItem("10").getSecond().intValue(), 1);
    Assert.assertEquals(sortedList.getItem("7").getFirst().intValue(), 10);
    Assert.assertEquals(sortedList.getItem("7").getSecond().intValue(), 1);
    Assert.assertEquals(sortedList.getItem("888").getFirst().intValue(), 8);
    Assert.assertEquals(sortedList.getItem("888").getSecond().intValue(), 4);
    Assert.assertEquals(sortedList.getItem("444").getFirst().intValue(), 4);
    Assert.assertEquals(sortedList.getItem("444").getSecond().intValue(), 8);

    testPrintSortedList(sortedList);
  }

  public static void testPrintSortedList(SortedList sortedList) {
    System.out.println("********************");
    System.out.println(sortedList.size());
    List<SortedListEntity> topList = sortedList.subList(1, sortedList.size());
    topList.forEach(entity -> System.out.println(entity.getMember() + ": " + entity.getScore()));
  }

}
