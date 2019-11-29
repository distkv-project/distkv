package com.distkv.dst.test.core.operator;

import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.test.supplier.BaseTestSupplier;
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
      store.sortLists().incrItem("k1", "xswl",1);
    }).start();

    Runnable runnable = () -> {
      store.sortLists().incrItem("k1", "fw",1);
    };
    new Thread(runnable).start();
    new Thread(runnable).start();

    Thread.sleep(100);
    List<SortedListEntity> k11 = store.sortLists().top("k1", 3);
    Assert.assertEquals(k11.get(1).getMember(),"fw");
    Assert.assertEquals(k11.get(1).getScore(),11);
    Assert.assertEquals(k11.get(2).getMember(),"xswl");
    Assert.assertEquals(k11.get(2).getScore(),10);
  }
}
