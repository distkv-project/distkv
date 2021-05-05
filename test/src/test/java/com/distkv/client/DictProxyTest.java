package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class DictProxyTest extends BaseTestSupplier {

  @Test
  public void testDictPutGet() {
    DistkvClient client = newDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    Map<String, String> dict1 = client.dicts().get("m1");
    client.drop("m1");
    Assert.assertEquals(dict, dict1);
    client.disconnect();
  }

  @Test
  public void testDictPutItem() {
    DistkvClient client = newDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    client.dicts().putItem("m1", "k2", "v2");
    final Map<String, String> m2 = client.dicts().get("m1");
    dict.put("k2", "v2");
    client.drop("m1");
    Assert.assertEquals(dict, m2);
    client.disconnect();
  }

  @Test
  public void testDictGetItemValue() {
    DistkvClient client = newDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    String s1 = client.dicts().getItem("m1", "k1");
    client.drop("m1");
    Assert.assertEquals("v1", s1);
    client.disconnect();
  }

  @Test
  public void testDictPopItem() {
    DistkvClient client = newDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    client.dicts().put("m1", dict);
    String s1 = client.dicts().popItem("m1", "k1");
    Assert.assertEquals("v1", s1);
    dict.remove("k1");
    Assert.assertEquals(dict, client.dicts().get("m1"));
    client.drop("m1");
    client.disconnect();
  }

  @Test
  public void testDictDrop() {
    DistkvClient client = newDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    client.dicts().put("m1", dict);
    client.drop("m1");
    client.disconnect();
  }

  @Test
  public void testKeyNotFoundException() {
    DistkvClient client = newDistkvClient();
    try {
      client.drop("m1");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
      return;
    } finally {
      client.disconnect();
    }
    Assert.fail();
  }

  @Test
  public void testExpireDict() {
    DistkvClient client = newDistkvClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    client.expire("m1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.dicts().get("m1");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }

}
