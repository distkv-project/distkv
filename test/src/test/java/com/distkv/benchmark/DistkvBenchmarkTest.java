package com.distkv.benchmark;

import com.distkv.client.DistkvClient;
import com.distkv.supplier.TestUtil;
import com.google.protobuf.InvalidProtocolBufferException;

public class DistkvBenchmarkTest {


  public static void strPutStressTest(DistkvClient client) throws InvalidProtocolBufferException {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = Thread.currentThread().getName();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      client.strs().put(name + i, "com/distkv/" + i);
    }
    String as = client.strs().get(name + 59999);
    long end = System.currentTimeMillis();
    String str = "This is Dsitkv Str put test. I'm thread-" +
        id +
        " and waste time =" +
        (end - start) +
        "; the result is " +
        "com/dsitkv/test59999".equals(as);
    System.out.println(str);
  }

  public static void benchmarkTest(DistkvClient client) throws InvalidProtocolBufferException {
    strPutStressTest(client);
    client.disconnect();
  }

  public static void main(String[] args) {
    // Distkv benchmark test
    TestUtil.startRpcServer(8082);
    DistkvBenchmark benchmark = new DistkvBenchmark(10);
    benchmark.setTestModule(distkvClient -> {
      try {
        benchmarkTest(distkvClient);
      } catch (InvalidProtocolBufferException e) {
        e.printStackTrace();
      }
    });
    benchmark.run();
    TestUtil.stopProcess(TestUtil.getProcess());
  }
}
