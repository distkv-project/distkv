package com.distkv.benchmark;

import com.distkv.dst.asyncclient.DefaultAsyncClient;
import com.distkv.dst.asyncclient.DstAsyncClient;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 8)
public class DstBenchmark {

  private DstAsyncClient client;
  private String value = randomString(5);

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(DstBenchmark.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }

  @Setup
  public void init() {
    TestUtil.startRpcServer(8082);
    client= new DefaultAsyncClient("list://127.0.0.1:8082");
    client.strs().put("k1", value);
  }

  @TearDown
  public void stop() {
    TestUtil.stopRpcServer();
  }

  @Benchmark
  public void strPut() {
    client.strs().put("k2", value);
  }

 /* @Benchmark
  public void strGet() {
    client.strs().get("k1");
  }*/

  /**
   * Get a random string pass by a length
   *
   * @param x Length of Random String
   * @return Random String
   */
  public static String randomString(int x) {
    String str = "1234567890abcdefghijklmnopqrstuvwxyz";
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < x; i++) {
      stringBuilder.append(str.charAt(random.nextInt(str.length())));
    }
    return stringBuilder.toString();
  }
}
