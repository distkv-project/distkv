package org.dst.test.client;

import java.util.Set;
import com.google.common.collect.ImmutableSet;
import org.dst.client.DstClient;
import org.dst.common.exception.DstException;
import org.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SetProxyTest extends BaseTestSupplier {

  @Test
  public void testPutAndGet() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set);
    Assert.assertEquals(set, client.sets().get("k1"));
  }

  @Test
  public void testDelete() {
    Set<String> set1 = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set1);
    client.sets().delete("k1", "v3");

    Set<String> set2 = ImmutableSet.of("v1", "v2");
    Assert.assertEquals(set2, client.sets().get("k1"));
  }

  @Test(expectedExceptions = DstException.class)
  public void testDropByKey() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set);
    Assert.assertTrue(client.sets().dropByKey("k1"));

    //if we drop the key in store, this method will throw a DstException
    client.sets().get("k1");
  }

  @Test(expectedExceptions = DstException.class)
  public void testExists() {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");

    DstClient client = newDstClient();
    client.sets().put("k1", set);
    Assert.assertTrue(client.sets().exists("k1", "v1"));

    client.sets().delete("k1", "v1");
    Assert.assertFalse(client.sets().exists("k1", "v1"));

    client.sets().dropByKey("k1");
    //if we drop the key in store, this method will throw a DstException
    client.sets().exists("k1", "v1");
  }
}
