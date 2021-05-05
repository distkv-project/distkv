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

public class SlistProxyTest extends BaseTestSupplier {

  private static final Logger LOG = LoggerFactory.getLogger(SlistProxyTest.class);

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
    testDrop();
    distkvClient.disconnect();
  }

  private void testTop() throws InvalidProtocolBufferException {
    LinkedList<SlistEntity> list = distkvClient.slists().top("slist_p_key", 100);
    Assert.assertEquals(list.get(0).getMember(), "whhh");
    Assert.assertEquals(list.get(1).getMember(), "fw");
  }

  private void testRemoveItem() {
    distkvClient.slists().removeMember("slist_p_key", "55");
  }

  private void testPutItem() {
    distkvClient.slists().putMember("slist_p_key", new SlistEntity("whhh", 100));
  }

  private void testIncItem() {
    distkvClient.slists().incrScore("slist_p_key", "fw", 1);
  }

  private void testPut() {
    LinkedList<SlistEntity> list = new LinkedList<>();
    list.add(new SlistEntity("xswl", 9));
    list.add(new SlistEntity("wlll", 8));
    list.add(new SlistEntity("fw", 9));
    list.add(new SlistEntity("55", 6));
    distkvClient.slists().put("slist_p_key", list);
  }

  private void testGetItem() throws InvalidProtocolBufferException {
    DistkvTuple<Integer, Integer> tuple = distkvClient.slists().getMember("slist_p_key", "fw");
    Assert.assertEquals(tuple.getFirst().intValue(), 10);
    Assert.assertEquals(tuple.getSecond().intValue(), 2);
  }

  private void testDrop() {
    distkvClient.drop("slist_p_key");
  }

  @Test
  public void testDistkvKeyDuplicatedException() {
    distkvClient = newDistkvClient();
    testPut();
    Assert.assertThrows(
        DistkvKeyDuplicatedException.class, this::testPut);
    testDrop();
    distkvClient.disconnect();
  }

  @Test
  public void testKeyNotFoundException() {
    distkvClient = newDistkvClient();
    Assert
        .assertThrows(KeyNotFoundException.class, () ->
            distkvClient.slists().top("slist_p_key", 100));
    distkvClient.disconnect();
  }

  @Test
  public void testExpireSList() {
    distkvClient = newDistkvClient();
    testPut();
    distkvClient.expire("slist_p_key", 1000);
    boolean result = RuntimeUtil.waitForCondition(() -> {
      try {
        distkvClient.slists().getMember("slist_p_key", "fw");
        return false;
      } catch (KeyNotFoundException e) {
        return true;
      } catch (InvalidProtocolBufferException e) {
        LOG.error("Failed to unpack response. {1}", e);
        return false;
      }
    }, 30 * 1000);
    Assert.assertTrue(result);
    distkvClient.disconnect();
  }

}
