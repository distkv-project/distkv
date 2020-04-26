package com.distkv.core.operator;

import com.distkv.common.entity.sortedList.SlistEntity;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import com.distkv.core.struct.slist.Slist;
import com.distkv.core.struct.slist.SlistLinkedImpl;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedList;
import java.util.List;

public class KVSSlistTest {

  @Test
  public void testSlist() {
    KVStore store = new KVStoreImpl();

    LinkedList<SlistEntity> list = new LinkedList<>();
    list.add(new SlistEntity("xswl", 9));
    list.add(new SlistEntity("wlll", 8));
    list.add(new SlistEntity("fw", 10));
    list.add(new SlistEntity("55", 6));
    Slist slist = new SlistLinkedImpl();
    slist.put(list);
    store.sortLists().put("k1", slist);
    store.sortLists().putMember("k1", new SlistEntity("asd", 1000));

    store.sortLists().incrScore("k1", "xswl",1);
    store.sortLists().incrScore("k1", "fw",1);
    List<SlistEntity> k11 = store.sortLists().top("k1",3);
    Assert.assertEquals(k11.get(1).getMember(),"fw");
    Assert.assertEquals(k11.get(1).getScore(),11);
    Assert.assertEquals(k11.get(2).getMember(),"xswl");
    Assert.assertEquals(k11.get(2).getScore(),10);
  }
}
