package com.distkv.client;

import java.util.LinkedList;
import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;


// TODO(qwang): Do not use so much clients. Use a final static client instead.
public class SortedListProxyTest extends BaseTestSupplier {

  private DistkvClient distkvClient = null;

  @Test
  public void testMain() {
    distkvClient = newDstClient();
    testPut();
    testIncItem();
    testPutItem();
    testTop();
    testRemoveItem();
    testTop();
    testGetItem();
    distkvClient.disconnect();
  }

  private void testTop() {
    LinkedList<SortedListEntity> list = distkvClient.sortedLists().top("k1", 100);
    Assert.assertEquals(list.get(0).getMember(), "whhh");
    Assert.assertEquals(list.get(1).getMember(),"fw");
  }

  private void testRemoveItem() {
    distkvClient.sortedLists().removeMember("k1","55");
  }

  private void testPutItem() {
    distkvClient.sortedLists().putMember("k1", new SortedListEntity("whhh",100));
  }

  private void testIncItem() {
    distkvClient.sortedLists().incrScore("k1", "fw",1);
  }

  private void testPut() {
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    distkvClient.sortedLists().put("k1", list);
  }

  private void testGetItem() {
    DistKVTuple<Integer, Integer> tuple = distkvClient.sortedLists().getMember("k1", "fw");
    Assert.assertEquals(tuple.getFirst().intValue(), 10);
    Assert.assertEquals(tuple.getSecond().intValue(), 2);
  }

}
