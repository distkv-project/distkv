package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class BasicOperationTest extends BaseTestSupplier {

  private void dummyPut(DistkvClient client) throws InvalidProtocolBufferException {
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
  }

  @Test
  public void testDrop() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    //Drop expiration.
    client.drop("k1");
    Assert.assertThrows(KeyNotFoundException.class, () -> client.strs().get("k1"));
    client.disconnect();
  }

  @Test
  public void testExpire() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    //Expire operation.
    client.expire("k1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.strs().get("k1");
        return false;
      } catch (InvalidProtocolBufferException e) {
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }

  @Test
  public void testExist() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    // Exist operation.
    Assert.assertTrue(client.exists("k1"));
    client.disconnect();
  }

  @Test
  public void testTTL() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    // TTL with no expire operation.
    Assert.assertEquals(client.ttl("k1"), -1);
    client.disconnect();
  }

  @Test
  public void testTTLWithExpire() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);

    //Expire operation.
    client.expire("k1", 2000);

    // TTL key not expire operation.
    boolean ttlResult = RuntimeUtil.waitForCondition(() -> {
      long servivalTime = client.ttl("k1");
      return servivalTime < 2000 && servivalTime > 0;
    }, 1000);
    Assert.assertTrue(ttlResult);

    // TTL key expire operation.
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.ttl("k1");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);

    client.disconnect();
  }


}
