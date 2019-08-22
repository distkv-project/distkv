package test.org.dst.client;

import java.util.HashSet;
import java.util.Set;
import org.dst.client.DefaultDstClient;
import org.dst.exception.DstException;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;

public class SetProxyTest extends BaseTestSupplier {

  private final static String serverAddress = "list://127.0.0.1:8082";

  @Test
  public void testPutAndGet() {
    Set<String> set = new HashSet<>();
    set.add("v1");
    set.add("v2");
    set.add("v3");
    set.add("v2");

    DefaultDstClient client = new DefaultDstClient(serverAddress);
    client.sets().put("k1", set);
    Assert.assertEquals(set, client.sets().get("k1"));
  }

  @Test
  public void testDelete() {
    Set<String> set = new HashSet<>();
    set.add("v1");
    set.add("v2");
    set.add("v3");

    DefaultDstClient client = new DefaultDstClient(serverAddress);
    client.sets().put("k1", set);
    client.sets().delete("k1", "v3");

    set.remove("v3");
    Assert.assertEquals(set, client.sets().get("k1"));
  }

  @Test
  public void testDropByKey() {
    Set<String> set = new HashSet<>();
    set.add("v1");
    set.add("v2");
    set.add("v3");

    DefaultDstClient client = new DefaultDstClient(serverAddress);
    client.sets().put("k1", set);
    client.sets().dropByKey("k1");

    //if we drop the key in store, this method will throw a DstException
    try {
      client.sets().get("k1");
    } catch (DstException e) {
      Assert.assertTrue(true);
    }

  }

  @Test
  public void testExists() {
    Set<String> set = new HashSet<>();
    set.add("v1");
    set.add("v2");
    set.add("v3");

    DefaultDstClient client = new DefaultDstClient(serverAddress);
    client.sets().put("k1", set);
    Assert.assertTrue(client.sets().exists("k1", "v1"));

    client.sets().delete("k1", "v1");
    Assert.assertFalse(client.sets().exists("k1", "v1"));

    client.sets().dropByKey("k1");
    //if we drop the key in store, this method will throw a DstException
    try {
      client.sets().exists("k1", "v1");
    } catch (DstException e) {
      Assert.assertTrue(true);
    }

  }
}
