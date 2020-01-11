package com.distkv.dst.test.mastersalversync;

import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.test.supplier.MasterSalverTestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMasterSalverSync {

  @Test
  public void testStrPut() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    Thread.sleep(5000);

    DstClient client = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));
    Assert.assertEquals("v1", client1.strs().get("k1"));
    client.disconnect();
    client1.disconnect();
    MasterSalverTestUtil.stopAllProcess();
  }
}
