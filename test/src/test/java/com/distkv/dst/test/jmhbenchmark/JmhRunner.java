package com.distkv.dst.test.jmhbenchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhRunner {

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
            .include(DstStrBenchmark.class.getSimpleName())
            .include(RedisStrBenchmark.class.getSimpleName())
            .include(DstListBenchmark.class.getSimpleName())
            .warmupIterations(5)
            .measurementIterations(8)
            .forks(1)
            .build();
    new Runner(option).run();
  }
}
