package com.distkv.benchmark.core.concepts.slist;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.core.struct.slist.SortedListRBTreeImpl;

import java.util.Collections;
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
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DstSortedListPutItemBenchmark {

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
        .include(DstSortedListPutItemBenchmark.class.getSimpleName())
        .warmupIterations(3)
        .measurementIterations(5)
        .forks(1)
        .build();
    new Runner(option).run();
  }

  private SortedListEntity sortedListEntity;

  private SortedList sortedListLinkedImpl;

  private SortedList sortedListRBTreeImpl;

  @Setup
  public void init() {
    final int maxLengthInList = 1000000;
    final int minValueInList = -1000000;
    final int maxValueInList = 1000000;

    sortedListLinkedImpl = new SortedListLinkedImpl();
    sortedListRBTreeImpl = new SortedListRBTreeImpl();

    List<SortedListEntity> initList = generatePutDatas(
        minValueInList, maxValueInList, maxLengthInList);
    sortedListLinkedImpl.put(initList);
    sortedListRBTreeImpl.put(initList);
    Collections.sort(initList);

    int score = initList.get(initList.size() - 1).getScore();
    sortedListEntity = new SortedListEntity(String.valueOf(score - 1), score - 1);
  }

  @Benchmark
  public void testPutItemLinkedImpl(Blackhole blackhole) {
    sortedListLinkedImpl.putItem(sortedListEntity);
  }

  @Benchmark
  public void testPutItemRBTreeImpl(Blackhole blackhole) {
    sortedListRBTreeImpl.putItem(sortedListEntity);
  }

  private static SortedListEntity generatePutItemData(
      int minValue, int maxValue) {
    int randomValue = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
    // Generate the entity whose score is [minValue, maxValue].
    SortedListEntity entity = new SortedListEntity(
        String.valueOf(randomValue), randomValue);
    return entity;
  }

  private static List<SortedListEntity> generatePutDatas(
      int minValue, int maxValue, int len) {
    List<SortedListEntity> list = new LinkedList<>();
    for (int i = 0; i < len; i++) {
      list.add(generatePutItemData(minValue, maxValue));
    }
    return list;
  }
}
