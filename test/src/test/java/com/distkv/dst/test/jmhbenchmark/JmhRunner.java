package com.distkv.dst.test.jmhbenchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;

public class JmhRunner {

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(StrDstBenchmark.class.getSimpleName())
        .include(StrRdsBenchmark.class.getSimpleName())
        .include(ListDstBenchmark.class.getSimpleName())
        .output(System.getProperty("user.dir") + File.separator
            + "test" + File.separator + "target"
            + File.separator + "StrDstBenchmark.log")
        .warmupIterations(5)
        .measurementIterations(8)
        //.mode(Mode.All)
        .forks(2)
        .build();
    new Runner(opt).run();
  }
}
