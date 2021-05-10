package com.distkv.client;

import com.distkv.common.utils.RuntimeUtil;
import com.distkv.supplier.BaseTestSupplier;
import com.google.common.collect.ImmutableList;
import com.distkv.common.exception.KeyNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class ListProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    //Test get all.
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3", "v4"),
        client.lists().get("list_p_k1"));
    //Test get one.
    Assert.assertEquals(ImmutableList.of("v3"),
        client.lists().get("list_p_k1", 2));
    //Test get range.
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client.lists().get("list_p_k1", 1, 3));
    client.drop("list_p_k1");
    //Test KeyNotFoundException.
    Assert.assertThrows(KeyNotFoundException.class, () -> client.lists().get("k2"));
  }

  @Test
  public void testDrop() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1", ImmutableList.of("v1", "v2", "v3"));
    client.drop("list_p_k1");
    Assert.assertThrows(KeyNotFoundException.class, () -> client.lists().get("list_p_k1"));
    client.disconnect();
  }

  @Test
  public void testLPut() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().lput("list_p_k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v4", "v5","v1", "v2", "v3"),
        client.lists().get("list_p_k1"));
    Assert.assertThrows(KeyNotFoundException.class,
        () -> client.lists().lput("k2", ImmutableList.of("v4", "v5")));
    client.drop("list_p_k1");
    client.disconnect();
  }

  @Test
  public void testRPut() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().rput("list_p_k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3","v4", "v5"),
        client.lists().get("list_p_k1"));
    Assert.assertThrows(KeyNotFoundException.class,
        () -> client.lists().rput("k2", ImmutableList.of("v4", "v5")));
    client.drop("list_p_k1");
    client.disconnect();
  }

  @Test
  public void testRemove() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1",
        ImmutableList.of("v1", "v2", "v3", "v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3", "v4", "v5"),
        client.lists().get("list_p_k1"));
    client.lists().remove("list_p_k1", 4);
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3", "v4"),
        client.lists().get("list_p_k1"));
    client.lists().remove("list_p_k1", 1, 2);
    Assert.assertEquals(ImmutableList.of("v1", "v3", "v4"),
        client.lists().get("list_p_k1"));
    Assert.assertThrows(KeyNotFoundException.class,
        () -> client.lists().remove("k2", 1));
    client.drop("list_p_k1");
    client.disconnect();
  }

  @Test
  public void testMRemove() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(3);
    client.lists().mremove("list_p_k1", list);
    Assert.assertEquals(ImmutableList.of("v1", "v3"), client.lists().get("list_p_k1"));
    Assert.assertThrows(KeyNotFoundException.class, () -> client.lists().mremove("k2", list));
    client.drop("list_p_k1");
    client.disconnect();
  }

  @Test
  public void testExpireList() {
    DistkvClient client = newDistkvClient();
    client.lists().put("list_p_k1", ImmutableList.of("v1", "v2", "v3"));
    client.expire("list_p_k1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.lists().get("list_p_k1");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }
}
