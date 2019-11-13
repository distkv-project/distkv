package org.dst.test.benchmark;

import org.dst.client.DstClient;
import org.dst.test.supplier.TestUtil;

public class DstBenchmarkTest {


  public static void strPutStressTest(DstClient client) {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = Thread.currentThread().getName();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      client.strs().put(name + i, "org/dst/test" + i);
    }
    String as = client.strs().get(name + 59999);
    long end = System.currentTimeMillis();
    String str = "This is DST Str put test. I'm thread-" +
        id +
        " and waste time =" +
        (end - start) +
        "; the result is " +
        "org/dst/test59999".equals(as);
    System.out.println(str);
  }

  public static void benchmarkTest(DstClient client) {
    strPutStressTest(client);
    client.disconnect();
  }

  public static void main(String[] args) {
    // DST benchmark test
    TestUtil.startRpcServer(8082);
    DSTBenchmark benchmark = new DSTBenchmark(10);
    benchmark.setTestModule(dstClient -> benchmarkTest(dstClient));
    benchmark.run();
    TestUtil.stopRpcServer();
  }
}
