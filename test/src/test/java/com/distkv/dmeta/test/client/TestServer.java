package com.distkv.dmeta.test.client;

import com.distkv.dmeta.client.DmetaClient;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dmeta.test.util.TestUtil;

public class TestServer {

  @Test
  public void testPutAndGet() throws InterruptedException {
    TestUtil.startAllNode();
    Thread.sleep(10000);
    DmetaClient client = new DmetaClient();

    client.createPath("woooo&kkk");
    client.putKV("test", "result","woooo&kkk");
    String result = client.getValue("woooo&kkk&test");

    TestUtil.stopAllNode();
    Assert.assertEquals(result, "result");
  }
}
