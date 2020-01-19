package com.distkv.benchmark.core.concepts;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;

import com.distkv.core.struct.slist.SortedListRBTreeImpl;
import java.util.LinkedList;
import java.util.List;

public class DstSortedListsRBTreeImplSimpleBenchmark {

  private static final int maxOperationTimes = 10000;

  private static final int minValueInPutList = -100000;

  private static final int maxValueInPutList = 100000;

  private static final int lengthInPutList = 100000;

  public static void main(String[] args) {
    System.out.println("Start run benchmark...........\n\n\n");
    long start = System.currentTimeMillis();

    // Test SortedListRBTreeImpl.put
    double totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      SortedList sortedList = new SortedListRBTreeImpl();
      List<SortedListEntity> list = generatePutDatas(minValueInPutList,
          maxValueInPutList, lengthInPutList);
      double consume = testPut(sortedList, list);
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("Method benchmark                 Cnt        Magnitude" +
        "  Average weighted time(ns)     Total weighted time(ns)");
    System.out.println("SortedListRBTreeImpl.put        " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                      " + totalTimes);

    // Test SortedListRBTreeImpl.putItem
    SortedList sortedList = new SortedListRBTreeImpl();
    List<SortedListEntity> list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      SortedListEntity entity = generatePutItemDatas(minValueInPutList - 1000,
          maxValueInPutList + 1000);
      double consume = testPutItem(sortedList, entity);
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedListRBTreeImpl.putItem    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "             " + totalTimes);

    // Test SortedListRBTreeImpl.removeItem
    sortedList = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testRemoveItem(sortedList, str);
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedListRBTreeImpl.removeItem " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListRBTreeImpl.incrScore
    sortedList = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      DistKVTuple<String, Integer> tuple = generateIncrScoreDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testIncrScore(sortedList, tuple.getFirst(), tuple.getSecond());
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedListRBTreeImpl.incrScore  " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListRBTreeImpl.subList
    sortedList = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      DistKVTuple<Integer, Integer> tuple = generateSubListDatas(sortedList.size());
      double consume = testSubList(sortedList, tuple.getFirst(), tuple.getSecond());;
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedListRBTreeImpl.subList    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListRBTreeImpl.getItem
    sortedList = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testGetItem(sortedList, str);
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedListRBTreeImpl.getItem    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    long end = System.currentTimeMillis();
    System.out.println("\n\n\nBenchmarking takes time " + (end - start) + "ms");
  }

  private static long testPut(
      SortedList sortedList, List<SortedListEntity> sortedListEntities) {
    long start = System.nanoTime();
    sortedList.put(sortedListEntities);
    long end = System.nanoTime();
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
    long start = System.nanoTime();
    sortedList.putItem(sortedListEntity);
    long end = System.nanoTime();
    return end - start;
  }

  private static SortedListEntity generatePutItemDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return new SortedListEntity(String.valueOf(randomValue), randomValue);
  }

  private static long testRemoveItem(
      SortedList sortedList, String member) {
    long start = System.nanoTime();
    sortedList.removeItem(member);
    long end = System.nanoTime();
    return end - start;
  }

  private static String generateRemoveItemDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return String.valueOf(randomValue);
  }

  private static long testIncrScore(
      SortedList sortedList, String member, int delta) {
    long start = System.nanoTime();
    sortedList.incrScore(member, delta);
    long end = System.nanoTime();
    return end - start;
  }

  private static DistKVTuple<String, Integer> generateIncrScoreDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    int randomDelta = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return new DistKVTuple<>(String.valueOf(randomValue), randomDelta);
  }

  private static long testSubList(
      SortedList sortedList, int startIndex, int endIndex) {
    long start = System.nanoTime();
    sortedList.subList(startIndex, endIndex);
    long end = System.nanoTime();
    return end - start;
  }

  private static DistKVTuple<Integer, Integer> generateSubListDatas(
      int size) {
    int startIndex = (int) Math.random() * size + 1;
    int len = (int) (Math.random() * (size - startIndex + 1));
    return new DistKVTuple<>(startIndex, startIndex + len);
  }

  private static long testGetItem(
      SortedList sortedList, String member) {
    long start = System.nanoTime();
    sortedList.getItem(member);
    long end = System.nanoTime();
    return end - start;
  }

  private static double addWeight(
      SortedList sortedList, double consume) {
    return consume / (sortedList.size() + 1);
  }
}
