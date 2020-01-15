package com.distkv.dst.client;

import com.distkv.dst.common.exception.KeyNotFoundException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.distkv.dst.supplier.BaseTestSupplier;

import java.util.Set;

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
    client.disconnect();
  }

  @Test
  public void testAllTypeProxiesTogether() {
    DstClient client = newDstClient();

    // string
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));

    // list
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"),client.lists().get("k1"));
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client.lists().get("k1", 1, 3));

    // set
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client.sets().put("k1", set);
    Assert.assertEquals(set, client.sets().get("k1"));
    client.disconnect();
  }
}
