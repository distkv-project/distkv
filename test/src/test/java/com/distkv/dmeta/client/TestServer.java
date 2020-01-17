package com.distkv.dmeta.client;

import com.distkv.supplier.DmetaTestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestServer {

  @Test
  public void testPutAndGet() throws InterruptedException {
    DmetaTestUtil.startAllDmetaProcess();
    TimeUnit.SECONDS.sleep(10);
    DmetaClient client = new DmetaClient();

    client.createPath("woooo&kkk");
    client.putKV("test", "result", "woooo&kkk");
    String result = client.getValue("woooo&kkk&test");

    DmetaTestUtil.stopAllDmetaProcess();
    Assert.assertEquals(result, "result");
    TimeUnit.SECONDS.sleep(3);
  }
}
