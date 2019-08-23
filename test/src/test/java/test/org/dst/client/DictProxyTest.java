package test.org.dst.client;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.exception.DstException;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;
import java.util.HashMap;
import java.util.Map;

public class DictProxyTest extends BaseTestSupplier {

  private static final String serverAddress = "list://127.0.0.1:8082";

  @Test
  public void testDictPutGet() {
    DstClient client = new DefaultDstClient(serverAddress);
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    Map<String, String> dict1 = client.dicts().get("m1");
    Assert.assertEquals(dict, dict1);
  }

  @Test
  public void testDictPutItem() {
    DstClient client = new DefaultDstClient(serverAddress);
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    client.dicts().putItem("m1", "k2", "v2");
    final Map<String, String> m2 = client.dicts().get("m1");
    dict.put("k2", "v2");
    Assert.assertEquals(dict, m2);
  }

  @Test
  public void testDictGetItemValue() {
    DstClient client = new DefaultDstClient(serverAddress);
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    String s1 = client.dicts().getItemValue("m1", "k1");
    Assert.assertEquals("v1", s1);
  }

  @Test
  public void testDictPopItem() {
    DstClient client = new DefaultDstClient(serverAddress);
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    client.dicts().put("m1", dict);
    String s1 = client.dicts().popItem("m1", "k1");
    Assert.assertEquals("v1", s1);
    dict.remove("k1");
    Assert.assertEquals(dict, client.dicts().get("m1"));
  }

  @Test
  public void testDictDel() {
    DstClient client = new DefaultDstClient(serverAddress);
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    client.dicts().put("m1", dict);
    client.dicts().del("m1");
  }

  @Test
  public void testException() {
    DstClient client = new DefaultDstClient(serverAddress);
    try {
      client.dicts().del("m1");
    } catch (DstException e) {
      e.printStackTrace();
    }
  }
}
