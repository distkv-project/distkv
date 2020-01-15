package com.distkv.dst.test.mastersalversync;

import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.common.entity.sortedList.SortedListEntity;
import com.distkv.dst.test.supplier.MasterSalverTestUtil;
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
  public void testStrPut() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(5);

    DstClient client = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));
    Assert.assertEquals("v1", client1.strs().get("k1"));
    client.disconnect();
    client1.disconnect();
    MasterSalverTestUtil.stopAllProcess();
    TimeUnit.SECONDS.sleep(1);
  }

  @Test
  public void testListPut() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(5);

    DstClient client = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));

    client.lists().put("k1", ImmutableList.of("v1", "v2", "v3"));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"),client.lists().get("k1"));
    Assert.assertEquals(ImmutableList.of("v2", "v3"),
        client.lists().get("k1", 1, 3));

    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));
    Assert.assertEquals(ImmutableList.of("v1", "v2", "v3"),client1.lists().get("k1"));
    client.disconnect();
    client1.disconnect();
    MasterSalverTestUtil.stopAllProcess();
    TimeUnit.SECONDS.sleep(1);
  }

  @Test
  public void testSetPut() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(5);

    DstClient client = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));

    Set<String> set = ImmutableSet.of("v1", "v2", "v3");
    client.sets().put("k1", set);
    Assert.assertEquals(set, client.sets().get("k1"));

    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));
    Assert.assertEquals(set, client1.sets().get("k1"));
    client.disconnect();
    client1.disconnect();
    MasterSalverTestUtil.stopAllProcess();
    TimeUnit.SECONDS.sleep(1);
  }

  @Test
  public void testDictPut() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(5);
    DstClient client = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));

    Map<String, String> dict = new HashMap<>();
    dict.put("k1", "v1");
    client.dicts().put("m1", dict);
    Map<String, String> dict1 = client.dicts().get("m1");
    Assert.assertEquals(dict, dict1);

    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));
    Map<String, String> dict2 = client1.dicts().get("m1");
    Assert.assertEquals(dict, dict2);
    client.disconnect();
    client1.disconnect();
    MasterSalverTestUtil.stopAllProcess();
    TimeUnit.SECONDS.sleep(1);
  }

  @Test
  public void testSlistPut() throws InterruptedException {
    MasterSalverTestUtil.startAllProcess();
    TimeUnit.SECONDS.sleep(5);
    DstClient client = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8082));

    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));
    client.sortedLists().put("k1", list);

    LinkedList<SortedListEntity> tlist = client.sortedLists().top("k1", 100);
    Assert.assertEquals(tlist.get(0).getMember(), "xswl");
    Assert.assertEquals(tlist.get(1).getMember(),"fw");

    DstClient client1 = new DefaultDstClient(String.format("list://127.0.0.1:%d", 8090));
    LinkedList<SortedListEntity> tlist1 = client1.sortedLists().top("k1", 100);
    Assert.assertEquals(tlist1.get(0).getMember(), "xswl");
    Assert.assertEquals(tlist1.get(1).getMember(),"fw");

    client.disconnect();
    client1.disconnect();
    MasterSalverTestUtil.stopAllProcess();
    TimeUnit.SECONDS.sleep(1);
  }
}
