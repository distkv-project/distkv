package com.distkv.dst.test.benchmark.core.concepts;

import com.distkv.dst.common.DstTuple;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.core.struct.slist.SortedList;
import com.distkv.dst.core.struct.slist.SortedListLinkedImpl;

import java.util.LinkedList;
import java.util.List;

public class DstSortedListsImplSimpleBenchmark {

  private static final int maxOperationtimes = 10;

  private static final int minValueInPutList = -100000;

  private static final int maxValueInPutList = 100000;

  private static final int lengthInPutList = 1000000;

  public static void main(String[] args) {
    System.out.println("Start run benchmark...........\n\n\n");

    // Test SortedListLinkedImpl.put
    double totalTimes = 0;
    for (int i = 0; i < maxOperationtimes; i++) {
      SortedList sortedList = new SortedListLinkedImpl();
      List<SortedListEntity> list = generatePutDatas(minValueInPutList,
          maxValueInPutList, lengthInPutList);
      totalTimes += testPut(sortedList, list);
    }
    System.out.println("Method benchmark                Cnt      Magnitude" +
        "      Average time      Total time");
    System.out.println("SortedListLinkedImpl.put        " + maxOperationtimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationtimes +
        "               " + totalTimes);

    // Test SortedListLinkedImpl.putItem
    SortedList sortedList = new SortedListLinkedImpl();
    List<SortedListEntity> list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationtimes; i++) {
      SortedListEntity entity = generatePutItemDatas(minValueInPutList - 1000,
          maxValueInPutList + 1000);
      totalTimes += testPutItem(sortedList, entity);
    }
    System.out.println("SortedListLinkedImpl.putItem    " + maxOperationtimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationtimes +
        "               " + totalTimes);

    // Test SortedListLinkedImpl.removeItem
    sortedList = new SortedListLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationtimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      totalTimes += testRemoveItem(sortedList, str);
    }
    System.out.println("SortedListLinkedImpl.removeItem " + maxOperationtimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationtimes +
        "               " + totalTimes);

    // Test SortedListLinkedImpl.incrScore
    sortedList = new SortedListLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationtimes; i++) {
      DstTuple<String, Integer> tuple = generateIncrScoreDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      totalTimes += testIncrScore(sortedList, tuple.getFirst(), tuple.getSecond());
    }
    System.out.println("SortedListLinkedImpl.incrScore  " + maxOperationtimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationtimes +
        "               " + totalTimes);

    // Test SortedListLinkedImpl.subList
    sortedList = new SortedListLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationtimes; i++) {
      DstTuple<Integer, Integer> tuple = generateSubListDatas(sortedList.getSize());
      totalTimes += testSubList(sortedList, tuple.getFirst(), tuple.getSecond());
    }
    System.out.println("SortedListLinkedImpl.subList    " + maxOperationtimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationtimes +
        "               " + totalTimes);

    // Test SortedListLinkedImpl.getItem
    sortedList = new SortedListLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationtimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      totalTimes += testGetItem(sortedList, str);
    }
    System.out.println("SortedListLinkedImpl.getItem    " + maxOperationtimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationtimes +
        "               " + totalTimes);
  }

  private static long testPut(
      SortedList sortedList, List<SortedListEntity> sortedListEntities) {
    long start = System.currentTimeMillis();
    sortedList.put(sortedListEntities);
    long end = System.currentTimeMillis();
    return end - start;
  }

  private static List<SortedListEntity> generatePutDatas(
      int minValue, int maxValue, int len) {
    List<SortedListEntity> list = new LinkedList<>();
    for (int i = 0; i < len; i++) {
      int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      list.add(new SortedListEntity(String.valueOf(randomValue), randomValue));
    }
    return list;
  }

  private static long testPutItem(
      SortedList sortedList, SortedListEntity sortedListEntity) {
    long start = System.currentTimeMillis();
    sortedList.putItem(sortedListEntity);
    long end = System.currentTimeMillis();
    return end - start;
  }

  private static SortedListEntity generatePutItemDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return new SortedListEntity(String.valueOf(randomValue), randomValue);
  }

  private static long testRemoveItem(
      SortedList sortedList, String member) {
    long start = System.currentTimeMillis();
    sortedList.removeItem(member);
    long end = System.currentTimeMillis();
    return end - start;
  }

  private static String generateRemoveItemDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return String.valueOf(randomValue);
  }

  private static long testIncrScore(
      SortedList sortedList, String member, int delta) {
    long start = System.currentTimeMillis();
    sortedList.incrScore(member, delta);
    long end = System.currentTimeMillis();
    return end - start;
  }

  private static DstTuple<String, Integer> generateIncrScoreDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    int randomDelta = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return new DstTuple<>(String.valueOf(randomValue), randomDelta);
  }

  private static long testSubList(
      SortedList sortedList, int sIndex, int eIndex) {
    long start = System.currentTimeMillis();
    sortedList.subList(sIndex, eIndex);
    long end = System.currentTimeMillis();
    return end - start;
  }

  private static DstTuple<Integer, Integer> generateSubListDatas(
      int size) {
    int sIndex = (int) Math.random() * size + 1;
    int len = (int) (Math.random() * (size - sIndex + 1));
    return new DstTuple<>(sIndex, sIndex + len);
  }

  private static long testGetItem(
      SortedList sortedList, String member) {
    long start = System.currentTimeMillis();
    sortedList.getItem(member);
    long end = System.currentTimeMillis();
    return end - start;
  }

}
