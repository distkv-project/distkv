package com.distkv.dst.test.client;

import com.distkv.dst.client.DstClient;
import com.distkv.dst.common.exception.KeyNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dst.test.supplier.BaseTestSupplier;

public class StringProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() {
    DstClient client = newDstClient();
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
    client.disconnect();
  }

  @Test
  public void testKeyNotFoundWhenGetting() {
    DstClient client = newDstClient();
    try {
      client.strs().get("k1");
      Assert.fail("It shouldn't reach here.");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
      client.disconnect();
    }
    // FIXME(qwang): Resources leak.
    client.disconnect();
  }
}
