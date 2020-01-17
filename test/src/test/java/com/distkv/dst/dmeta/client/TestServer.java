package com.distkv.dst.dmeta.client;

import com.distkv.dmeta.client.DmetaClient;
import com.distkv.dst.supplier.DmetaTestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestServer {

  @Test
  public void testPutAndGet() throws InterruptedException {
    DmetaTestUtil.startAllDmetaProcess();
    Thread.sleep(10000);
    DmetaClient client = new DmetaClient();

    client.createPath("woooo&kkk");
    client.putKV("test", "result", "woooo&kkk");
    String result = client.getValue("woooo&kkk&test");

    DmetaTestUtil.stopAllDmetaProcess();
    Assert.assertEquals(result, "result");
    Thread.sleep(3000);
  }
}
