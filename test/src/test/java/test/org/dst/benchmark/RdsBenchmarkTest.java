package test.org.dst.benchmark;

import redis.clients.jedis.Jedis;
import test.org.dst.benchmark.core.Benchmark;

public class RdsBenchmarkTest {
  public static void strPutStressTest(Jedis jedis) {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = Thread.currentThread().getName();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      jedis.set(name + i, "test" + i);
    }
    String as = jedis.get(name + 59999);
    long end = System.currentTimeMillis();
    String str = "This test is RDS Str put test, and this is thread-" +
        id +
        " and waste time =" +
        (end - start) +
        "; the result is " +
        "test59999".equals(as);
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
