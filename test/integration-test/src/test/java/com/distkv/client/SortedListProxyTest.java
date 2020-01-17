package com.distkv.client;

import java.util.LinkedList;
import com.distkv.common.DstTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import org.testng.Assert;
import org.testng.annotations.Test;


// TODO(qwang): Do not use so much clients. Use a final static client instead.
public class SortedListProxyTest extends BaseTestSupplier {

  private DstClient dstClient = null;

  @Test
  public void testMain() {
    dstClient = newDstClient();
    testPut();
    testIncItem();
    testPutItem();
    testTop();
    testRemoveItem();
    testTop();
    testGetItem();
    dstClient.disconnect();
  }

  private void testTop() {
    LinkedList<SortedListEntity> list = dstClient.sortedLists().top("k1", 100);
    Assert.assertEquals(list.get(0).getMember(), "whhh");
    Assert.assertEquals(list.get(1).getMember(),"fw");
  }

  private void testRemoveItem() {
    dstClient.sortedLists().removeMember("k1","55");
  }

  private void testPutItem() {
    dstClient.sortedLists().putMember("k1", new SortedListEntity("whhh",100));
  }

  private void testIncItem() {
    dstClient.sortedLists().incrScore("k1", "fw",1);
  }

  private void testPut() {
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    dstClient.sortedLists().put("k1", list);
  }

  private void testGetItem() {
    DstTuple<Integer, Integer> tuple = dstClient.sortedLists().getMember("k1", "fw");
    Assert.assertEquals(tuple.getFirst().intValue(), 10);
    Assert.assertEquals(tuple.getSecond().intValue(), 2);
  }

}
