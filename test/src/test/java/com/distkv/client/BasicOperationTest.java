package com.distkv.client;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.distkv.supplier.BaseTestSupplier;
import com.google.protobuf.InvalidProtocolBufferException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicOperationTest extends BaseTestSupplier {

  private void dummyPut(DistkvClient client) throws InvalidProtocolBufferException {
    client.strs().put("b_key", "v1");
    Assert.assertEquals("v1", client.strs().get("b_key"));
  }

  @Test
  public void testDrop() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    //Drop expiration.
    client.drop("b_key");
    Assert.assertThrows(KeyNotFoundException.class, () -> client.strs().get("b_key"));
    client.disconnect();
  }

  @Test
  public void testExpiration() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    //Expire operation.
    client.expire("b_key", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.strs().get("b_key");
        return false;
      } catch (InvalidProtocolBufferException e) {
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 3 * 1000);
    Assert.assertTrue(result);
    client.disconnect();
  }

  @Test
  public void testExists() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    // Exist operation.
    Assert.assertTrue(client.exists("b_key"));
    client.drop("b_key");
    client.disconnect();
  }

  @Test
  public void testTTL() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);
    // TTL with no expire operation.
    Assert.assertEquals(client.ttl("b_key"), -1);
    client.drop("b_key");
    client.disconnect();
  }

  @Test
  public void testTTLWithExpiration() throws InvalidProtocolBufferException {
    DistkvClient client = newDistkvClient();
    dummyPut(client);

    //Expire operation.
    client.expire("b_key", 2000);

    // TTL with key has not expired operation.
    boolean ttlResult = RuntimeUtil.waitForCondition(() -> {
      long servivalTime = client.ttl("b_key");
      return servivalTime < 2000 && servivalTime > 0;
    }, 1000);
    Assert.assertTrue(ttlResult);

    // TTL with key has expired operation.
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        client.ttl("b_key");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      }
    }, 3 * 1000);
    Assert.assertTrue(result);

    client.disconnect();
  }


}
