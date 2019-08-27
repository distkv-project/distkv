package test.org.dst.benchmark;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.org.dst.supplier.TestUtil;
import java.util.ArrayList;
import java.util.List;

public class DSTBenchmark {

  private static final String serverAddress = "list://127.0.0.1:8082";

  private static final int THREAD_NUM = 10;

  private static final Logger LOGGER = LoggerFactory.getLogger(DSTBenchmark.class);

  public static void main(String[] args) {
    TestUtil.startRpcServer();
    test();
    try {
      Thread.sleep(100 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    TestUtil.stopRpcServer();
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
    LOGGER.debug("This test is Str put test, and this is thread-" + id + " and waste time =" + (end - start) + "; the result is " + "test59999".equals(as));
    client.disconnect();
  }

  public static void test() {
    List<DstClient> list = new ArrayList<>();
    for (int i = 0; i < THREAD_NUM; i++) {
      list.add(new DefaultDstClient(serverAddress));
    }
    for (int i = 0; i < list.size(); i++) {
      int finalI = i;
      new Thread(() -> strPutStressTest(list.get(finalI))).start();
    }
  }


}
