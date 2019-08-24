package test.org.dst.client;

import com.google.common.collect.ImmutableList;
import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.exception.KeyNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;

public class ListProxyTest extends BaseTestSupplier {

  private static final String serverAddress = "list://127.0.0.1:8082";

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testPutAndGet() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().get("k2");
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDel() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().del("k1");
    //exception test
    client.lists().get("k1");
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testLPut() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().lput("k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v4", "v5","v1", "v2", "v3"),client.lists().get("k1"));
    //exception test
    client.lists().lput("k2", ImmutableList.of("v4", "v5"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRPut() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    client.lists().rput("k1", ImmutableList.of("v4", "v5"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3","v4", "v5"),client.lists().get("k1"));
    //exception test
    client.lists().rput("k2", ImmutableList.of("v4", "v5"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testLDel() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    client.lists().ldel("k1",2);
    Assert.assertEquals(ImmutableList.of("v3", "v4"),client.lists().get("k1"));
    //exception test
    client.lists().ldel("k2",1);
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRDel() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3", "v4"));
    client.lists().rdel("k1",2);
    Assert.assertEquals(ImmutableList.of("v1", "v2"),client.lists().get("k1"));
    //exception test
    client.lists().rdel("k2",1);
  }


}
