package com.distkv.jmhbenchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhRunner {

  public static void main(String[] args) throws RunnerException {
    Options option = new OptionsBuilder()
            .include(DistkvStrBenchmark.class.getSimpleName())
            .include(RedisStrBenchmark.class.getSimpleName())
            .include(DistkvListBenchmark.class.getSimpleName())
            .warmupIterations(5)
            .measurementIterations(8)
            .forks(1)
            .build();
    new Runner(option).run();
  }
}
