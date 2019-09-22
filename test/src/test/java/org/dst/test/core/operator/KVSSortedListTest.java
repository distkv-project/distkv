package org.dst.test.core.operator;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.entity.SortedListEntity;
import org.dst.test.supplier.BaseTestSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedList;
import java.util.List;

public class KVSSortedListTest extends BaseTestSupplier {
  @Test
  public void testSortedList() throws InterruptedException {
    KVStore store = new KVStoreImpl();

    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    store.sortLists().put("k1", list);
    store.sortLists().putItem("k1", new SortedListEntity("asd",1000));
    List<SortedListEntity> k1 = store.sortLists().top("k1", 2);
    new Thread(() -> {
      store.sortLists().incItem("k1", "xswl");
    }).start();

    Runnable runnable = () -> {
      store.sortLists().incItem("k1", "fw");
    };
    new Thread(runnable).start();
    new Thread(runnable).start();

    Thread.sleep(100);
    List<SortedListEntity> k11 = store.sortLists().top("k1", 3);
    Assert.assertEquals(k11.get(1).getInfo(),"fw");
    Assert.assertEquals(k11.get(1).getScore(),11);
    Assert.assertEquals(k11.get(2).getInfo(),"xswl");
    Assert.assertEquals(k11.get(2).getScore(),10);
  }
}
