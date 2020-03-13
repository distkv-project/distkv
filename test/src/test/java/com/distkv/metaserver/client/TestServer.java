package com.distkv.metaserver.client;

import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.supplier.DmetaTestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestServer {

  @Test
  public void testPutAndGet() throws InterruptedException {
    System.out.println(String.format("\n==================== Running the test method: %s.%s",
        "DmetaTest", "testPutAndGet"));
    DmetaTestUtil.startAllDmetaProcess();
    TimeUnit.SECONDS.sleep(10);
    DmetaClient client = new DmetaClient();

    try {
      client.put("a", "b");
      client.put("c", "d");
      String result = client.get("a");
      Assert.assertEquals(result, "b");
      TimeUnit.SECONDS.sleep(3);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DmetaTestUtil.stopAllDmetaProcess();
    }
  }
}
