package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Set;

public class StringProxyTest extends BaseTestSupplier {

  private static final Logger LOG = LoggerFactory.getLogger(StringProxyTest.class);

  @Test
  public void testPutAndGet() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    client.strs().put("str_k2", "v2");
    Assert.assertEquals("v2", client.strs().get("str_k2"));
    client.disconnect();
  }

  @Test
  public void testKeyNotFoundWhenGetting() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    try {
      client.strs().get("str_key");
      Assert.fail("It shouldn't reach here.");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
      client.disconnect();
    }
    client.disconnect();
  }

  @Test
  public void testAllTypeProxiesTogether() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();

    // string
    client.strs().put("str_p_k1", "v1");
    Assert.assertEquals("v1", client.strs().get("str_p_k1"));

    // list
    client.lists().put("list_k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"), client.lists().get("list_k1"));
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client.lists().get("list_k1", 1, 3));

    // set
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client.sets().put("set_k1", set);
    Assert.assertEquals(set, client.sets().get("set_k1"));
    client.disconnect();
  }

  @Test
  public void testExpireStr() {
    DistkvClient client = newDistkvClient();
    client.strs().put("expired_k1", "v1");
    client.expire("expired_k1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.strs().get("expired_k1");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      } catch (InvalidProtocolBufferException e) {
        LOG.error("Failed to unpack response. {1}", e);
        return false;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }

}
