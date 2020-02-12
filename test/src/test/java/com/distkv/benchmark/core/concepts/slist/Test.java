package com.distkv.benchmark.core.concepts.slist;

import com.distkv.common.entity.sortedList.SortedListEntity;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.Assert;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Test {

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
        .include(DstSortedListPutItemBenchmark.class.getSimpleName())
        .warmupIterations(5)
        .measurementIterations(8)
        .forks(1)
        .build();
    new Runner(option).run();
  }

  private LinkedList<Integer> linkedImpl;

  private TreeMap<Integer, String> rbTreeImpl;

  private Integer score;

  @Setup
  public void init() {
    final int maxLengthInList = 1000000;
    final int minValueInList = -1000000;
    final int maxValueInList = 1000000;

    linkedImpl = new LinkedList<>();
    rbTreeImpl = new TreeMap<>();

    List<Integer> initList = generatePutDatas(
        minValueInList, maxValueInList, maxLengthInList);
    Collections.sort(initList);
    for (final Integer integer : initList) {
      linkedImpl.add(integer);
      rbTreeImpl.put(integer, null);
    }

    score = initList.get(initList.size() - 1) + 1;
    Assert.assertTrue(score > initList.get(0));
  }

  @Benchmark
  public void testPutItemLinkedImpl() {
    ListIterator<Integer> iterator = linkedImpl.listIterator();
    while (iterator.hasNext()) {
      if (iterator.next().compareTo(score) < 0) {
        iterator.add(score);
        break;
      }
    }
  }

  @Benchmark
  public void testPutItemRBTreeImpl() {
    rbTreeImpl.put(score, null);
  }

  private static SortedListEntity generatePutItemData(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    // Generate the entity whose score is [minValue, maxValue].
    SortedListEntity entity = new SortedListEntity(
        String.valueOf(randomValue), randomValue);
    return entity;
  }

  private static List<Integer> generatePutDatas(
      int minValue, int maxValue, int len) {
    List<Integer> list = new LinkedList<>();
    Set<Integer> set = new HashSet<>();
    Integer randomValue = null;
    for (int i = 0; i < len; i++) {
      randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      while (set.contains(randomValue)) {
        randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
      }
      list.add(randomValue);
    }
    return list;
  }
}
