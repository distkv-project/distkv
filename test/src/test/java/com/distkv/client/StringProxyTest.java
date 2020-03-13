package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Set;

@Test(singleThreaded = true)
public class StringProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
    client.disconnect();
  }

  @Test
  public void testKeyNotFoundWhenGetting() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    try {
      client.strs().get("k1");
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
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));

    // list
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"), client.lists().get("k1"));
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client.lists().get("k1", 1, 3));

    // set
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client.sets().put("k1", set);
    Assert.assertEquals(set, client.sets().get("k1"));
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testExpireStr() throws InvalidProtocolBufferException, InterruptedException {
    DistkvClient client = newDistkvClient();
    client.strs().put("k1", "v1");
    client.strs().expire("k1", 1);
    Thread.sleep(2000);
    client.strs().get("k1");
    client.disconnect();
  }

}
