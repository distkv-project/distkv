package com.distkv.benchmark.core.concepts.slist;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class DstSortedListRunner {

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
        .include(DstSortedListPutBenchmark.class.getSimpleName())
        .include(DstSortedListPutItemBenchmark.class.getSimpleName())
        .warmupIterations(3)
        .measurementIterations(3)
        .forks(1)
        .build();
    new Runner(option).run();
  }
}
