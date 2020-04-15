package com.distkv.client;

import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.RuntimeUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedList;
import com.distkv.common.DistkvTuple;
import com.distkv.supplier.BaseTestSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class SlistProxyTest extends BaseTestSupplier {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlistProxyTest.class);

  private DistkvClient distkvClient = null;

  @Test
  public void testMain() throws InvalidProtocolBufferException {
    distkvClient = newDistkvClient();
    testPut();
    testIncItem();
    testPutItem();
    testTop();
    testRemoveItem();
    testTop();
    testGetItem();
    distkvClient.disconnect();
  }

  private void testTop() throws InvalidProtocolBufferException {
    LinkedList<SlistEntity> list = distkvClient.sortedLists().top("k1", 100);
    Assert.assertEquals(list.get(0).getMember(), "whhh");
    Assert.assertEquals(list.get(1).getMember(), "fw");
  }

  private void testRemoveItem() {
    distkvClient.sortedLists().removeMember("k1", "55");
  }

  private void testPutItem() {
    distkvClient.sortedLists().putMember("k1", new SlistEntity("whhh", 100));
  }

  private void testIncItem() {
    distkvClient.sortedLists().incrScore("k1", "fw", 1);
  }

  private void testPut() {
    LinkedList<SlistEntity> list = new LinkedList<>();
    list.add(new SlistEntity("xswl", 9));
    list.add(new SlistEntity("wlll", 8));
    list.add(new SlistEntity("fw", 9));
    list.add(new SlistEntity("55", 6));
    distkvClient.sortedLists().put("k1", list);
  }

  private void testGetItem() throws InvalidProtocolBufferException {
    DistkvTuple<Integer, Integer> tuple = distkvClient.sortedLists().getMember("k1", "fw");
    Assert.assertEquals(tuple.getFirst().intValue(), 10);
    Assert.assertEquals(tuple.getSecond().intValue(), 2);
  }

  @Test
  public void testDistkvKeyDuplicatedException() {
    distkvClient = newDistkvClient();
    testPut();
    Assert.assertThrows(
        DistkvKeyDuplicatedException.class, this::testPut);
    distkvClient.disconnect();
  }

  @Test
  public void testKeyNotFoundException() {
    distkvClient = newDistkvClient();
    Assert
        .assertThrows(KeyNotFoundException.class, () ->
            distkvClient.sortedLists().top("k1", 100));
    distkvClient.disconnect();
  }

  @Test
  public void testExpireSList() {
    distkvClient = newDistkvClient();
    testPut();
    distkvClient.expire("k1", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        distkvClient.sortedLists().getMember("k1", "fw");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      } catch (InvalidProtocolBufferException e) {
        LOGGER.error("Failed to unpack response. {1}", e);
        return false;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    distkvClient.disconnect();
  }

}
