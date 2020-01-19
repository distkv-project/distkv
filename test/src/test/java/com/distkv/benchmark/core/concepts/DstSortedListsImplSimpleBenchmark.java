package com.distkv.benchmark.core.concepts;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.core.struct.slist.SortedListRBTreeImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DstSortedListsImplSimpleBenchmark {

  private static final int maxOperationTimes = 100000;

  private static final int minValueInPutList = 0;

  private static final int maxValueInPutList = 100000;

  private static final int maxLengthInPutList = 100000;

  private static String[] strings;

  static {
    strings = new String[maxValueInPutList + 1];
    for (int i = 0; i <= maxValueInPutList; i++) {
      strings[i] = String.valueOf(i);
    }
  }

  public static void main(String[] args) {
    System.out.println("Start run benchmark...........\n\n\n");
    long start = System.currentTimeMillis();

    // To test put
    double totalLinkedImplTimes = 0;
    double totalRBTreeImplTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      SortedList sortedListLinkedImpl = new SortedListLinkedImpl();
      SortedList sortedListRBTreeImpl = new SortedListRBTreeImpl();
      List<SortedListEntity> list = generatePutDatas(minValueInPutList,
          maxValueInPutList, maxLengthInPutList);
      List<SortedListEntity> copy = new ArrayList<>(list);
      double consume = testPut(sortedListLinkedImpl, list);
      totalLinkedImplTimes += addWeight(sortedListLinkedImpl, consume);
      consume = testPut(sortedListRBTreeImpl, copy);
      totalRBTreeImplTimes += addWeight(sortedListRBTreeImpl, consume);

    }
    System.out.println("Method benchmark                 Cnt        Magnitude" +
        "  Average weighted time(ns)     Total weighted time(ns)");
    System.out.println("SortedListLinkedImpl.put        " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalLinkedImplTimes / maxOperationTimes +
        "                  " + totalLinkedImplTimes);
    System.out.println("SortedListRBTreeImpl.put        " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalRBTreeImplTimes / maxOperationTimes +
        "                  " + totalRBTreeImplTimes);

    // To test putItem
    SortedList sortedListLinked = new SortedListLinkedImpl();
    SortedList sortedListRBTree = new SortedListRBTreeImpl();
    List<SortedListEntity> list = generatePutDatas(minValueInPutList,
        maxValueInPutList, maxLengthInPutList);
    testPut(sortedListLinked, list);
    testPut(sortedListRBTree, list);
    // Generated putItem entities
    List<SortedListEntity> putItemList = generatePutDatas(
        minValueInPutList, maxValueInPutList, maxOperationTimes);
    totalLinkedImplTimes = 0;
    totalRBTreeImplTimes = 0;
    for (int i = 0; i < putItemList.size(); i++) {
      double consume = testPutItem(sortedListLinked, putItemList.get(i));
      totalLinkedImplTimes += addWeight(sortedListLinked, consume);
      consume = testPutItem(sortedListRBTree, putItemList.get(i));
      totalRBTreeImplTimes += addWeight(sortedListRBTree, consume);
    }
    System.out.println("SortedListLinkedImpl.putItem    " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalLinkedImplTimes / maxOperationTimes +
        "            " + totalLinkedImplTimes);
    System.out.println("SortedListRBTreeImpl.putItem    " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalRBTreeImplTimes / maxOperationTimes +
        "            " + totalRBTreeImplTimes);

    // To test removeItem
    sortedListLinked = new SortedListLinkedImpl();
    sortedListRBTree = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, maxLengthInPutList);
    testPut(sortedListLinked, list);
    testPut(sortedListRBTree, list);
    // Generated removeItem entities
    List<String> removeItemList = generateRemoveItemDatas(
        minValueInPutList, maxValueInPutList, maxOperationTimes);
    totalLinkedImplTimes = 0;
    totalRBTreeImplTimes = 0;
    for (int i = 0; i < removeItemList.size(); i++) {
      double consume = testRemoveItem(sortedListLinked, removeItemList.get(i));
      totalLinkedImplTimes += addWeight(sortedListLinked, consume);
      consume = testRemoveItem(sortedListRBTree, removeItemList.get(i));
      totalRBTreeImplTimes += addWeight(sortedListRBTree, consume);
    }
    System.out.println("SortedListLinkedImpl.removeItem " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalLinkedImplTimes / maxOperationTimes +
        "               " + totalLinkedImplTimes);
    System.out.println("SortedListRBTreeImpl.removeItem " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalRBTreeImplTimes / maxOperationTimes +
        "               " + totalRBTreeImplTimes);

    // To test incrScore
    sortedListLinked = new SortedListLinkedImpl();
    sortedListRBTree = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, maxLengthInPutList);
    testPut(sortedListLinked, list);
    testPut(sortedListRBTree, list);
    // Generated incrScore entities
    List<DistKVTuple<String, Integer>> incrScoreList = generateIncrScoreDatas(
        minValueInPutList, maxValueInPutList, maxOperationTimes);
    totalLinkedImplTimes = 0;
    totalRBTreeImplTimes = 0;
    for (int i = 0; i < incrScoreList.size(); i++) {
      double consume = testIncrScore(
          sortedListLinked, incrScoreList.get(i).getFirst(), incrScoreList.get(i).getSecond());
      totalLinkedImplTimes += addWeight(sortedListLinked, consume);
      consume = testIncrScore(
          sortedListRBTree, incrScoreList.get(i).getFirst(), incrScoreList.get(i).getSecond());
      totalRBTreeImplTimes += addWeight(sortedListRBTree, consume);
    }
    System.out.println("SortedListLinkedImpl.incrScore  " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalLinkedImplTimes / maxOperationTimes +
        "               " + totalLinkedImplTimes);
    System.out.println("SortedListRBTreeImpl.incrScore  " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalRBTreeImplTimes / maxOperationTimes +
        "               " + totalRBTreeImplTimes);

    // To test subList
    sortedListLinked = new SortedListLinkedImpl();
    sortedListRBTree = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, maxLengthInPutList);
    testPut(sortedListLinked, list);
    testPut(sortedListRBTree, list);
    // Generated subList entities
    List<DistKVTuple<Integer, Integer>> subListList = generateSubListDatas(
        maxLengthInPutList, maxOperationTimes);
    totalLinkedImplTimes = 0;
    totalRBTreeImplTimes = 0;
    for (int i = 0; i < subListList.size(); i++) {
      double consume = testSubList(
          sortedListLinked, subListList.get(i).getFirst(), subListList.get(i).getSecond());
      totalLinkedImplTimes += addWeight(sortedListLinked, consume);
      consume = testSubList(
          sortedListRBTree, subListList.get(i).getFirst(), subListList.get(i).getSecond());
      totalRBTreeImplTimes += addWeight(sortedListRBTree, consume);
    }
    System.out.println("SortedListLinkedImpl.subList    " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalLinkedImplTimes / maxOperationTimes +
        "               " + totalLinkedImplTimes);
    System.out.println("SortedListRBTreeImpl.sublist    " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalRBTreeImplTimes / maxOperationTimes +
        "               " + totalRBTreeImplTimes);

    // To test getItem
    sortedListLinked = new SortedListLinkedImpl();
    sortedListRBTree = new SortedListRBTreeImpl();
    list = generatePutDatas(minValueInPutList,
        maxValueInPutList, maxLengthInPutList);
    testPut(sortedListLinked, list);
    testPut(sortedListRBTree, list);
    // Generated getItem entities
    List<String> getItemList = generateRemoveItemDatas(
        minValueInPutList, maxValueInPutList, maxOperationTimes);
    totalLinkedImplTimes = 0;
    totalRBTreeImplTimes = 0;
    for (int i = 0; i < getItemList.size(); i++) {
      double consume = testGetItem(sortedListLinked, getItemList.get(i));
      totalLinkedImplTimes += addWeight(sortedListLinked, consume);
      consume = testGetItem(sortedListRBTree, getItemList.get(i));
      totalRBTreeImplTimes += addWeight(sortedListRBTree, consume);
    }
    System.out.println("SortedListLinkedImpl.getItem    " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalLinkedImplTimes / maxOperationTimes +
        "               " + totalLinkedImplTimes);
    System.out.println("SortedListRBTreeImpl.getItem    " + maxOperationTimes +
        "        " + maxLengthInPutList + "          " + totalRBTreeImplTimes / maxOperationTimes +
        "               " + totalRBTreeImplTimes);

    long end = System.currentTimeMillis();
    System.out.println("\n\n\nBenchmarking takes time " + (end - start) / 1000 / 60.0 + " min");
  }

  private static List<DistKVTuple<Integer, Integer>> generateSubListDatas(
      int lengthInPutList, int len) {
    List<DistKVTuple<Integer, Integer>> tuples = new LinkedList<>();
    for (int i = 0; i < len; i++) {
      int randomStart = (int) (Math.random() * lengthInPutList) + 1;
      int randomLength = (int) Math.random() * (lengthInPutList - randomStart + 1);
      tuples.add(new DistKVTuple<>(randomStart, randomStart + randomLength));
    }
    return tuples;
  }

  private static List<DistKVTuple<String, Integer>> generateIncrScoreDatas(
      int minValue, int maxValue, int len) {
    List<DistKVTuple<String, Integer>> tuples = new LinkedList<>();
    for (int i = 0; i < len; i++) {
      int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      int randomDelta = (int) (Math.random() * Integer.MAX_VALUE) + 1;
      tuples.add(new DistKVTuple<>(strings[randomValue], randomDelta));
    }
    return tuples;
  }

  private static List<String> generateRemoveItemDatas(
      int minValue, int maxValue, int len) {
    List<String> list = new LinkedList<>();
    for (int i = 0; i < len; i++) {
      int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      list.add(strings[randomValue]);
    }
    return list;
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
      // Generated the entity which score is [minValue, maxValue].
      int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      list.add(new SortedListEntity(strings[randomValue], randomValue));
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

  private static long testRemoveItem(
      SortedList sortedList, String member) {
    long start = System.nanoTime();
    sortedList.removeItem(member);
    long end = System.nanoTime();
    return end - start;
  }

  private static long testIncrScore(
      SortedList sortedList, String member, int delta) {
    long start = System.nanoTime();
    sortedList.incrScore(member, delta);
    long end = System.nanoTime();
    return end - start;
  }

  private static long testSubList(
      SortedList sortedList, int startIndex, int endIndex) {
    long start = System.nanoTime();
    sortedList.subList(startIndex, endIndex);
    long end = System.nanoTime();
    return end - start;
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
