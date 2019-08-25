package test.org.dst.stress;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.testng.Assert;
import org.testng.annotations.*;
import test.org.dst.supplier.TestUtil;

import java.lang.reflect.Method;

public class TestDSTStress {

  public static void main(String[] args) {
    TestUtil.startRpcServer();
    strPutStressTest();
    TestUtil.stopRpcServer();
  }

  private static final String serverAddress = "list://127.0.0.1:8082";

  public static void strPutStressTest() {
    int numthread = 10;
    for(int i = 0; i < numthread ; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          DstClient client = new DefaultDstClient(serverAddress);
          int max = 100000;
          long start = System.currentTimeMillis();
          for (long i = 0;i< max ; i++) {
            client.strs().put(start+"DST"+i,"testV"+(i-1));
          }
          String as = client.strs().get(start+"DST"+50000);
          long end = System.currentTimeMillis();
          System.out.println("============="+(end-start));
          Assert.assertEquals(as,"testV49999");
        }
      });
      thread.setName("thread-"+i);
      thread.start();
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
