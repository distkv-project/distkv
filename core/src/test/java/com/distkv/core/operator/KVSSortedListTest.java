package com.distkv.core.operator;

import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.common.exception.DistkvException;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

public class KVSSortedListTest {
  @Test
  public void testSortedList() throws DistkvException {
    KVStore store = new KVStoreImpl();

    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 10));
    list.add(new SortedListEntity("55", 6));
    store.sortLists().put("k1", list);
    store.sortLists().putMember("k1", new SortedListEntity("asd",1000));

    store.sortLists().incrScore("k1", "xswl",1);
    store.sortLists().incrScore("k1", "fw",1);
    List<SortedListEntity> k11 = store.sortLists().top("k1", 3);
    Assert.assertEquals(k11.get(1).getMember(),"fw");
    Assert.assertEquals(k11.get(1).getScore(),11);
    Assert.assertEquals(k11.get(2).getMember(),"xswl");
    Assert.assertEquals(k11.get(2).getScore(),10);
  }
}
