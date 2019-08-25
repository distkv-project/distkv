package test.org.dst.stress;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestStringStress {

  private static final String serverAddress = "list://127.0.0.1:8082";

  @Test(threadPoolSize = 5, invocationCount = 10)
  public void strPutStressTest() {
    DstClient client = new DefaultDstClient(serverAddress);
    int max = 100000;
    long start = System.currentTimeMillis();
    for (long i = 0;i< max ; i++) {
      client.strs().put(start+"Red!isTtest"+i,"testV"+(i-1));
    }
    String as = client.strs().get(start+"Red!isTtest"+50000);
    long end = System.currentTimeMillis();
    System.out.println(end-start);
    Assert.assertEquals(as,"testV49999");
  }

}
