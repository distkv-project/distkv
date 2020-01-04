package com.distkv.dst.test.jmhbenchmark;

import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import redis.clients.jedis.Jedis;

@State(Scope.Thread)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
public class StrRdsBenchmark {

  private Jedis jedis;

  @Setup
  public void init() {
    /*
     * When running, please make sure that the local redis
     * server is opened normally on port 6379.
     */
    jedis = new Jedis("127.0.0.1", 6379);
    jedis.set("k-redis", "v-redis");
  }

  @TearDown
  public void close() {
    jedis.close();
  }

  @Benchmark
  public void testStrGet(Blackhole bh) {
    bh.consume(jedis.get("k-redis"));
  }


  @Benchmark
  public void testStrPut(Blackhole bh) {
    String randomStr = RandomStringUtils.random(5);
    bh.consume(jedis.set(randomStr, randomStr));
  }

}