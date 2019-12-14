package com.distkv.dst.test.core.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.common.exception.KeyNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KVSListTest {

  private static List<String> listForKVSTest() {
    List<String> list = new ArrayList<>();
    list.add("v1");
    list.add("v2");
    list.add("v3");
    return list;
  }

  @Test
  public void testPutAndGet() {
    KVStore store = new KVStoreImpl();
    // Note that the list is `v1 v2 v3`.
    store.lists().put("k1", listForKVSTest());
    Assert.assertEquals(listForKVSTest(), store.lists().get("k1"));
    Assert.assertEquals(store.lists().get("k1", 1), "v2");
    Assert.assertEquals(store.lists().get("k1", 1, 3), ImmutableList.of("v2", "v3"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDrop() {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    Assert.assertEquals("ok", store.lists().drop("k1").toString());
    store.lists().get("k1");
  }

  @Test
  public void testLPut() {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    List<String> list2 = new ArrayList<>();
    list2.add("v4");
    list2.add("v5");
    store.lists().lput("k1", list2);
    Assert.assertEquals(Arrays.asList("v4", "v5", "v1", "v2", "v3"), store.lists().get("k1"));
  }

  @Test
  public void testRPut() {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    List<String> list2 = new ArrayList<>();
    list2.add("v4");
    list2.add("v5");
    store.lists().rput("k1", list2);
    Assert.assertEquals(Arrays.asList("v1", "v2", "v3", "v4", "v5"), store.lists().get("k1"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRemove() {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    store.lists().remove("k1", 0);
    Assert.assertEquals(Arrays.asList("v2", "v3"), store.lists().get("k1"));
    //test exceptions
    store.lists().remove("-k", 0).toString();
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testMRemove() {
    KVStore store = new KVStoreImpl();
    List<Integer> list = new ArrayList<>();
    list.add(2);
    list.add(0);
    store.lists().put("k1", listForKVSTest());
    store.lists().mremove("k1", list);
    Assert.assertEquals(ImmutableList.of("v2"), store.lists().get("k1"));
    //test exceptions
    list.clear();
    list.add(0);
    store.lists().mremove("-k", list).toString();
  }

}
