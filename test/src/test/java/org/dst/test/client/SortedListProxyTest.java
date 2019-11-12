package org.dst.test.client;

import java.util.LinkedList;
import org.dst.client.DstClient;
import org.dst.common.entity.sortedList.SortedListEntity;
import org.dst.test.supplier.BaseTestSupplier;
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
    testDelItem();
    testTop();
    dstClient.disconnect();
  }

  private void testTop() {
    LinkedList<SortedListEntity> list = dstClient.sortedLists().top("k1", 100);
    Assert.assertEquals(list.get(0).getMember(), "whhh");
    Assert.assertEquals(list.get(1).getMember(),"fw");
  }

  private void testDelItem() {
    dstClient.sortedLists().delItem("k1","55");
  }

  private void testPutItem() {
    dstClient.sortedLists().putItem("k1", new SortedListEntity("whhh",100));
  }

  private void testIncItem() {
    dstClient.sortedLists().incrItem("k1", "fw",1);
  }

  private void testPut() {
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    dstClient.sortedLists().put("k1", list);
  }

}
