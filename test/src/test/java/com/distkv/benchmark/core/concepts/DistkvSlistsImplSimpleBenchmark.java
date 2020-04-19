package com.distkv.benchmark.core.concepts;

import com.distkv.common.DistkvTuple;
import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.core.struct.slist.Slist;
import com.distkv.core.struct.slist.SlistLinkedImpl;

import java.util.LinkedList;
import java.util.List;

public class DistkvSlistsImplSimpleBenchmark {

  private static final int maxOperationTimes = 10000;

  private static final int minValueInPutList = -100000;

  private static final int maxValueInPutList = 100000;

  private static final int lengthInPutList = 10000;

  public static void main(String[] args) {
    System.out.println("Start run benchmark...........\n\n\n");
    long start = System.currentTimeMillis();

    // Test SortedListLinkedImpl.put
    double totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      Slist slist = new SlistLinkedImpl();
      List<SlistEntity> list = generatePutDatas(minValueInPutList,
          maxValueInPutList, lengthInPutList);
      double consume = testPut(slist, list);
      totalTimes += addWeight(slist, consume);
    }
    System.out.println("Method benchmark                 Cnt        Magnitude" +
        "  Average weighted time(ns)     Total weighted time(ns)");
    System.out.println("SortedListLinkedImpl.put        " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                      " + totalTimes);

    // Test SortedListLinkedImpl.putItem
    Slist slist = new SlistLinkedImpl();
    List<SlistEntity> list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(slist, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      SlistEntity entity = generatePutItemDatas(minValueInPutList - 1000,
          maxValueInPutList + 1000);
      double consume = testPutItem(slist, entity);
      totalTimes += addWeight(slist, consume);
    }
    System.out.println("SortedListLinkedImpl.putItem    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "             " + totalTimes);

    // Test SortedListLinkedImpl.removeItem
    slist = new SlistLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(slist, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testRemoveItem(slist, str);
      totalTimes += addWeight(slist, consume);
    }
    System.out.println("SortedListLinkedImpl.removeItem " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListLinkedImpl.incrScore
    slist = new SlistLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(slist, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      DistkvTuple<String, Integer> tuple = generateIncrScoreDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testIncrScore(slist, tuple.getFirst(), tuple.getSecond());
      totalTimes += addWeight(slist, consume);
    }
    System.out.println("SortedListLinkedImpl.incrScore  " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListLinkedImpl.subList
    slist = new SlistLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(slist, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      DistkvTuple<Integer, Integer> tuple = generateSubListDatas(slist.size());
      double consume = testSubList(slist, tuple.getFirst(), tuple.getSecond());;
      totalTimes += addWeight(slist, consume);
    }
    System.out.println("SortedListLinkedImpl.subList    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListLinkedImpl.getItem
    slist = new SlistLinkedImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(slist, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testGetItem(slist, str);
      totalTimes += addWeight(slist, consume);
    }
    System.out.println("SortedListLinkedImpl.getItem    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    long end = System.currentTimeMillis();
    System.out.println("\n\n\nBenchmarking takes time " + (end - start) + "ms");
  }

  private static long testPut(
      Slist slist, List<SlistEntity> sortedListEntities) {
    long start = System.nanoTime();
    slist.put(sortedListEntities);
    long end = System.nanoTime();
    return end - start;
  }

  private static List<SlistEntity> generatePutDatas(
      int minValue, int maxValue, int len) {
    List<SlistEntity> list = new LinkedList<>();
    for (int i = 0; i < len; i++) {
      int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      list.add(new SlistEntity(String.valueOf(randomValue), randomValue));
    }
    return list;
  }

  private static long testPutItem(
      Slist slist, SlistEntity slistEntity) {
    long start = System.nanoTime();
    slist.putItem(slistEntity);
    long end = System.nanoTime();
    return end - start;
  }

  private static SlistEntity generatePutItemDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return new SlistEntity(String.valueOf(randomValue), randomValue);
  }

  private static long testRemoveItem(
      Slist slist, String member) {
    long start = System.nanoTime();
    slist.removeItem(member);
    long end = System.nanoTime();
    return end - start;
  }

  private static String generateRemoveItemDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return String.valueOf(randomValue);
  }

  private static long testIncrScore(
      Slist slist, String member, int delta) {
    long start = System.nanoTime();
    slist.incrScore(member, delta);
    long end = System.nanoTime();
    return end - start;
  }

  private static DistkvTuple<String, Integer> generateIncrScoreDatas(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    int randomDelta = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    return new DistkvTuple<>(String.valueOf(randomValue), randomDelta);
  }

  private static long testSubList(
      Slist slist, int startIndex, int endIndex) {
    long start = System.nanoTime();
    slist.subList(startIndex, endIndex);
    long end = System.nanoTime();
    return end - start;
  }

  private static DistkvTuple<Integer, Integer> generateSubListDatas(
      int size) {
    int startIndex = (int) Math.random() * size + 1;
    int len = (int) (Math.random() * (size - startIndex + 1));
    return new DistkvTuple<>(startIndex, startIndex + len);
  }

  private static long testGetItem(
      Slist slist, String member) {
    long start = System.nanoTime();
    slist.getItem(member);
    long end = System.nanoTime();
    return end - start;
  }

  private static double addWeight(
      Slist slist, double consume) {
    return consume / (slist.size() + 1);
  }
}
