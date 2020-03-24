package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class IntProxyTest extends BaseTestSupplier {

  private static final Logger LOGGER = LoggerFactory.getLogger(IntProxyTest.class);

  @Test
  public void testPutGetIncrDrop() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    try {
      client.ints().put("k1", 1);
      Assert.assertEquals(1, client.ints().get("k1"));
      client.ints().incr("k1", 2);
      Assert.assertEquals(3, client.ints().get("k1"));
      //Assert.assertTrue(client.distkv().drop("k1"));
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

  @Test
  public void testExpireList() {
    DistkvClient client = newDistkvClient();
    client.ints().put("k1", 1);
    client.expire("k1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.ints().get("k1");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      } catch (InvalidProtocolBufferException e) {
        LOGGER.error("Failed to unpack response. {1}", e);
        return false;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }

}
