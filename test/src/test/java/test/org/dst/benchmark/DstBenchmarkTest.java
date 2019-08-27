package test.org.dst.benchmark;

import org.dst.client.DstClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.org.dst.benchmark.core.DSTBenchmark;

public class DstBenchmarkTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DSTBenchmark.class);

  public DstClient client;

  public DstBenchmarkTest(DstClient client) {
    this.client = client;
  }

  public static void strPutStressTest(DstClient client) {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = Thread.currentThread().getName();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      client.strs().put(name + i, "test" + i);
    }
    String as = client.strs().get(name + 59999);
    long end = System.currentTimeMillis();
    String str = "This test is Str put test, and this is thread-" +
        id + " and waste time =" +
        (end - start) + "; the result is " + "test59999".equals(as);
    LOGGER.debug(str);
    System.out.println(str);
    client.disconnect();
  }

}
