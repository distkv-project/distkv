package com.distkv.client;

import java.util.Set;

import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableSet;
import com.distkv.common.exception.DstException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SetProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set);
    Assert.assertEquals(set, client.sets().get("k1"));
    client.disconnect();
  }

  @Test
  public void testRemoveItem() {
    Set<String> set1 = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set1);
    client.sets().removeItem("k1", "v3");

    Set<String> set2 = ImmutableSet.of("v1", "v2");
    Assert.assertEquals(set2, client.sets().get("k1"));
    client.disconnect();
  }

  @Test(expectedExceptions = DstException.class)
  public void testDropByKey() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set);
    Assert.assertTrue(client.sets().drop("k1"));

    //if we drop the key in store, this method will throw a DstException
    client.sets().get("k1");
    client.disconnect();
  }

  @Test(expectedExceptions = DstException.class)
  public void testExists() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set);
    Assert.assertTrue(client.sets().exists("k1", "v1"));

    client.sets().removeItem("k1", "v1");
    Assert.assertFalse(client.sets().exists("k1", "v1"));

    client.sets().drop("k1");
    //if we drop the key in store, this method will throw a DstException
    client.sets().exists("k1", "v1");
    client.disconnect();
  }
}
