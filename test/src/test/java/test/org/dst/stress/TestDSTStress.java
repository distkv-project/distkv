package test.org.dst.stress;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import test.org.dst.supplier.TestUtil;
import java.util.ArrayList;
import java.util.List;

public class TestDSTStress {
//
//  public static void main(String[] args) {
//    TestUtil.startRpcServer();
//    test();
//    TestUtil.stopRpcServer();
//  }

  private static final String serverAddress = "list://127.0.0.1:8082";
  private static final Logger LOGGER = LoggerFactory.getLogger(TestDSTStress.class);
//  @Test(invocationCount = 10, threadPoolSize = 10)
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
    System.out.println("this is thread-"+id+" and waste time ="+(end - start)+"; the result is "+ "test59999".equals(as));
    client.disconnect();
  }

  @Test
  public static void test() {
    int max = 10;
    List<DstClient> list = new ArrayList<>();
    for(int i = 0;i < max ; i++) {
      list.add(new DefaultDstClient(serverAddress));
    }
    for(int i = 0 ;i < list.size() ; i++) {
      int finalI = i;
      new Thread(new Runnable() {
        @Override
        public void run() {
          strPutStressTest(list.get(finalI)) ;
        }
      }).start();
    }
    try {
      Thread.sleep(100*1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @BeforeClass
  public void setupBase() {
    TestUtil.startRpcServer();
  }

  @AfterClass
  public void teardownBase() {
    TestUtil.stopRpcServer();
  }

}
