package com.distkv.jmhbenchmark;

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
public class RedisStrBenchmark {

  private static final String IP = "127.0.0.1";
  private static final int PORT = 6379;
  private static final String KEY_REDIS = "k-redis";
  private static final String VALUE_REDIS = "v-redis";
  private Jedis jedis;

  @Setup
  public void init() {
    /*
     * When running, please make sure that the local redis
     * server is opened normally on port 6379.
     */
    jedis = new Jedis(IP, PORT);
    jedis.set(KEY_REDIS, VALUE_REDIS);
  }

  @TearDown
  public void close() {
    jedis.close();
  }

  @Benchmark
  public void testStrGet(Blackhole blackhole) {
    blackhole.consume(jedis.get(KEY_REDIS));
  }


  @Benchmark
  public void testStrPut(Blackhole blackhole) {
    String randomStr = RandomStringUtils.random(5);
    blackhole.consume(jedis.set(randomStr, randomStr));
  }

}
