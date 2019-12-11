package com.distkv.dst.test.client;

import com.distkv.dst.client.DstClient;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DictProxyTest extends BaseTestSupplier {

  @Test
  public void testDictPutGet() {
    DstClient client = newDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    Map<String, String> dict1 = client.dicts().get("m1");
    Assert.assertEquals(dict, dict1);
    client.disconnect();
  }

  @Test
  public void testDictPutItem() {
    DstClient client = newDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    client.dicts().putItem("m1", "k2", "v2");
    final Map<String, String> m2 = client.dicts().get("m1");
    dict.put("k2", "v2");
    Assert.assertEquals(dict, m2);
    client.disconnect();
  }

  @Test
  public void testDictGetItemValue() {
    DstClient client = newDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    String s1 = client.dicts().getItem("m1", "k1");
    Assert.assertEquals("v1", s1);
    client.disconnect();
  }

  @Test
  public void testDictPopItem() {
    DstClient client = newDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    client.dicts().put("m1", dict);
    String s1 = client.dicts().popItem("m1", "k1");
    Assert.assertEquals("v1", s1);
    dict.remove("k1");
    Assert.assertEquals(dict, client.dicts().get("m1"));
    client.disconnect();
  }

  @Test
  public void testDictDrop() {
    DstClient client = newDstClient();
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    client.dicts().put("m1", dict);
    client.dicts().drop("m1");
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testKeyNotFoundException() {
    DstClient client = newDstClient();
    client.dicts().drop("m1");
    // TODO(qwang): Might cause resources leak. Fix it ASAP.
    client.disconnect();
  }
}
