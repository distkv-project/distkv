package com.distkv.benchmark.core.concepts.slist;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.core.struct.slist.SortedListRBTreeImpl;

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
public class DstSortedListPutBenchmark {

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
        .include(DstSortedListPutBenchmark.class.getSimpleName())
        .warmupIterations(5)
        .measurementIterations(8)
        .forks(1)
        .build();
    new Runner(option).run();
  }

  private List<SortedListEntity> putList;

  private SortedList sortedListLinkedImpl;

  private SortedList sortedListRBTreeImpl;

  @Setup
  public void init() {
    final int maxLengthInList = 100;
    final int minValueInList = -100;
    final int maxValueInList = 100;

    sortedListLinkedImpl = new SortedListLinkedImpl();
    sortedListRBTreeImpl = new SortedListRBTreeImpl();

    putList = generatePutDatas(
        minValueInList, maxValueInList, maxLengthInList);
  }

  @Benchmark
  public void testPutLinkedImpl(Blackhole blackhole) {
    blackhole.consume(sortedListLinkedImpl.put(putList));
  }

  @Benchmark
  public void testPutRBTreeImpl(Blackhole blackhole) {
    blackhole.consume(sortedListRBTreeImpl.put(putList));
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
}
