package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import java.util.Set;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableSet;
import com.distkv.common.exception.DistkvException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SetProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DistkvClient client = newDistkvClient();
    client.sets().put("set_p_k1", set);
    Assert.assertEquals(set, client.sets().get("set_p_k1"));
    client.disconnect();
  }

  @Test
  public void testRemoveItem() {
    Set<String> set1 = ImmutableSet.of("v1", "v2", "v3");

    DistkvClient client = newDistkvClient();
    client.sets().put("set_p_k1", set1);
    client.sets().removeItem("set_p_k1", "v3");

    Set<String> set2 = ImmutableSet.of("v1", "v2");
    Assert.assertEquals(set2, client.sets().get("set_p_k1"));
    client.disconnect();
  }

  @Test
  public void testRemoveItemException() {
    Set<String> set1 = ImmutableSet.of("v1", "v2", "v3");

    DistkvClient client = newDistkvClient();
    client.sets().put("set_p_k1", set1);
    Assert.assertThrows(DistkvException.class,
        () -> client.sets().removeItem("set_p_k1", "v4"));

    client.disconnect();
  }

  @Test
  public void testDropByKey() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DistkvClient client = newDistkvClient();
    client.sets().put("set_p_k1", set);
    client.drop("set_p_k1");

    // This method will throw a DistkvException if we drop the nonexistent key in store.
    Assert.assertThrows(DistkvException.class,
        () -> client.sets().get("set_p_k1"));
    client.disconnect();
  }

  @Test
  public void testExists() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DistkvClient client = newDistkvClient();
    client.sets().put("set_p_k1", set);
    Assert.assertTrue(client.sets().exists("set_p_k1", "v1"));

    client.sets().removeItem("set_p_k1", "v1");
    Assert.assertFalse(client.sets().exists("set_p_k1", "v1"));

    client.drop("set_p_k1");
    // This method will throw a DistkvException if we drop the nonexistent key in store.
    Assert.assertThrows(KeyNotFoundException.class,
        () -> client.sets().exists("set_p_k1", "v1"));
    client.disconnect();
  }

  @Test
  public void testExpireSet() {
    DistkvClient client = newDistkvClient();
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client.sets().put("set_p_k1", set);
    client.expire("set_p_k1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.sets().get("set_p_k1");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }
}
