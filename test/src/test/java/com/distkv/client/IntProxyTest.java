package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class IntProxyTest extends BaseTestSupplier {

  @Test
  public void testPutGetIncrDrop() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    try {
      client.ints().put("k1", 1);
      Assert.assertEquals(1, client.ints().get("k1"));
      client.ints().incr("k1", 2);
      Assert.assertEquals(3, client.ints().get("k1"));
      Assert.assertTrue(client.ints().drop("k1"));
    } finally {
      client.disconnect();
    }
  }

  @Test
  public void testKeyNotFoundWhenGetting() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    try {
      client.ints().get("k1");
      Assert.fail("It shouldn't reach here.");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
    } finally {
      client.disconnect();
    }
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testExpireList() throws InterruptedException, InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    client.ints().put("k1", 1);
    client.ints().expire("k1", 1);
    Thread.sleep(4000);
    client.ints().get("k1");
    client.disconnect();
  }

}
