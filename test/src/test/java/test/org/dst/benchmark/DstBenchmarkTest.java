package test.org.dst.benchmark;

import org.dst.client.DstClient;
import test.org.dst.supplier.TestUtil;

public class DstBenchmarkTest {


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
    String str = "This test is DST Str put test, and this is thread-" +
        id +
        " and waste time =" +
        (end - start) +
        "; the result is " +
        "test59999".equals(as);
    System.out.println(str);
  }

  public static void benchmarkTest(DstClient client) {
    strPutStressTest(client);
    client.disconnect();
  }

  public static void main(String[] args) {
    // DST benchmark test
    TestUtil.startRpcServer();
    DSTBenchmark benchmark = new DSTBenchmark(10);
    benchmark.setTestModule(dstClient -> benchmarkTest(dstClient));
    benchmark.run();
    TestUtil.stopRpcServer();
  }
}
