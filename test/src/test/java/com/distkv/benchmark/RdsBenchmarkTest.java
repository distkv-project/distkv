package com.distkv.benchmark;

import com.distkv.benchmark.core.Benchmark;
import redis.clients.jedis.Jedis;

public class RdsBenchmarkTest {
  public static void strPutStressTest(Jedis jedis) {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = Thread.currentThread().getName();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      jedis.set(name + i, "com/distkv/dst" + i);
    }
    String as = jedis.get(name + 59999);
    long end = System.currentTimeMillis();
    String str = "This is RDS Str put test. I'm thread-" +
        id +
        " and waste time =" +
        (end - start) +
        "; the result is " +
        "org/dst/test59999".equals(as);
    System.out.println(str);
  }

  public static void benchmarkTest() {
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    strPutStressTest(jedis);
    jedis.close();
  }

  public static void main(String[] args) {
    //RDS benchmark test
    Benchmark benchmark = new Benchmark(10);
    benchmark.setTest(() -> benchmarkTest());
    benchmark.run();
  }
}
