package com.distkv.benchmark.core.concepts;

import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.core.struct.slist.SortedListRBTreeImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DstSortedListsImplBenchmark {

  private static final int minValueInPutList = 0;

  private static final int maxValueInPutList = 100000;

  private static String[] strings;

  static {
    strings = new String[maxValueInPutList + 1];
    for (int i = minValueInPutList; i <= maxValueInPutList; i++) {
      strings[i] = String.valueOf(i);
    }
  }

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
        .include(DstSortedListsImplBenchmark.class.getSimpleName())
        .warmupIterations(3)
        .measurementIterations(5)
        .forks(1)
        .build();
    new Runner(option).run();
  }

  private List<List<SortedListEntity>> putList;

  @Setup
  public void init() {
    final int maxOperationTimes = 1000;
    final int maxLengthInPutList = 1000;

    putList = new ArrayList<>();
    for (int i = 0; i < maxOperationTimes; i++) {
      putList.add(generatePutDatas(
          minValueInPutList, maxValueInPutList, maxLengthInPutList));
    }
  }

  @Benchmark
  public void testPutLinkedImpl(Blackhole blackhole) {
    SortedList sortedList = new SortedListLinkedImpl();
    for (int i = 0; i < putList.size(); i++) {
      List<SortedListEntity> copy = putList.get(i);
      blackhole.consume(sortedList.put(copy));
    }
  }

  @Benchmark
  public void testPutRBTreeImpl(Blackhole blackhole) {
    SortedList sortedList = new SortedListRBTreeImpl();
    for (int i = 0; i < putList.size(); i++) {
      List<SortedListEntity> copy = putList.get(i);
      blackhole.consume(sortedList.put(copy));
    }
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
      // Generate the entity whose score is [minValue, maxValue].
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
