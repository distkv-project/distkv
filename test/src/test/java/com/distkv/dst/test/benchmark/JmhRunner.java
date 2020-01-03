package com.distkv.dst.test.benchmark;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.io.File;

public class JmhRunner {

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(JmhBenchmark.class.getSimpleName())
        /*.output(System.getProperty("user.dir")+ File.separator + "target"
            + File.separator +"JmhBenchmark.log")*/
        .warmupIterations(5)
        .measurementIterations(8)
        .mode(Mode.All)
        .forks(3)
        .build();
    new Runner(opt).run();
  }
}
