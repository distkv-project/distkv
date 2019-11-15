package org.dst.test.client;

import com.google.common.collect.ImmutableList;
import org.dst.client.DstClient;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Vector;

public class ListProxyTest extends BaseTestSupplier {

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testPutAndGet() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().get("k2");
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDel() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().drop("k1");
    //exception test
    client.lists().get("k1");
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testLPut() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().lput("k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v4", "v5","v1", "v2", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().lput("k2", ImmutableList.of("v4", "v5"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRPut() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().rput("k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3","v4", "v5"),client.lists().get("k1"));
    //exception test
    client.lists().rput("k2", ImmutableList.of("v4", "v5"));
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDelete() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3", "v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3", "v4", "v5"),client.lists().get("k1"));
    client.lists().delete("k1", 4);
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3", "v4"), client.lists().get("k1"));
    client.lists().delete("k1", 1, 2);
    //exception test
    client.lists().delete("k2", 1);
    client.disconnect();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testMDelete() {
    DstClient client = newDstClient();
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    List<Integer> list = new Vector<Integer>();
    list.add(1);
    list.add(3);
    client.lists().mdelete("k1",list);
    Assert.assertEquals(ImmutableList.of("v1", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().mdelete("k2",list);
    client.disconnect();
  }


}
