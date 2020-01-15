package com.distkv.dst.mastersalversync;

import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.supplier.MasterSalverTestUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestMasterSalverSync {

  @Test
  public void mainTest() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(5);
    DstClient client0 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));
    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));

    //test
    testStrPut(client0, client1);
    testListPut(client0, client1);
    testSetPut(client0, client1);
    testDictPut(client0, client1);
    testSlistPut(client0, client1);

    MasterSalverTestUtil.stopAllProcess();
    TimeUnit.SECONDS.sleep(1);
  }

  public void testStrPut(DstClient client0, DstClient client1) {
    client0.strs().put("k1", "v1");
    Assert.assertEquals("v1", client0.strs().get("k1"));
    Assert.assertEquals("v1", client1.strs().get("k1"));
  }


  public void testListPut(DstClient client0, DstClient client1) {

    client0.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"), client0.lists().get("k1"));
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client0.lists().get("k1", 1, 3));

    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"), client1.lists().get("k1"));
  }


  public void testSetPut(DstClient client0, DstClient client1) {
    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client0.sets().put("k1", set);
    Assert.assertEquals(set, client0.sets().get("k1"));
    Assert.assertEquals(set, client1.sets().get("k1"));
  }


  public void testDictPut(DstClient client0, DstClient client1) {
    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client0.dicts().put("m1", dict);
    Map<String, String> dict1 = client0.dicts().get("m1");
    Assert.assertEquals(dict, dict1);

    Map<String, String> dict2 = client1.dicts().get("m1");
    Assert.assertEquals(dict, dict2);
  }


  public void testSlistPut(DstClient client0, DstClient client1) {
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    client0.sortedLists().put("k1", list);

    LinkedList<SortedListEntity> tlist = client0.sortedLists().top("k1", 100);
    Assert.assertEquals(tlist.get(0).getMember(), "fw");
    Assert.assertEquals(tlist.get(1).getMember(), "xswl");

    LinkedList<SortedListEntity> tlist1 = client1.sortedLists().top("k1", 100);
    Assert.assertEquals(tlist1.get(0).getMember(), "fw");
    Assert.assertEquals(tlist1.get(1).getMember(), "xswl");
  }
}
