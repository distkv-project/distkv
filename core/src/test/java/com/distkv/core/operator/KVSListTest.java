package com.distkv.core.operator;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import com.google.common.collect.ImmutableList;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KVSListTest {

  private static ArrayList<String> listForKVSTest() {
    ArrayList<String> list = new ArrayList<>();
    list.add("v1");
    list.add("v2");
    list.add("v3");
    return list;
  }

  @Test
  public void testPutAndGet() throws DistkvException {
    KVStore store = new KVStoreImpl();
    // Note that the list is `v1 v2 v3`.
    store.lists().put("k1", listForKVSTest());
    Assert.assertEquals(listForKVSTest(), store.lists().get("k1"));
    Assert.assertEquals(store.lists().get("k1", 1), "v2");
    Assert.assertEquals(store.lists().get("k1", 1, 3), ImmutableList.of("v2", "v3"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDrop() throws DistkvException {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    store.lists().drop("k1");
    store.lists().get("k1");
  }

  @Test
  public void testLPut() throws DistkvException {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    List<String> list2 = new ArrayList<>();
    list2.add("v4");
    list2.add("v5");
    store.lists().lput("k1", list2);
    Assert.assertEquals(Arrays.asList("v4", "v5", "v1", "v2", "v3"), store.lists().get("k1"));
  }

  @Test
  public void testRPut() throws DistkvException {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    List<String> list2 = new ArrayList<>();
    list2.add("v4");
    list2.add("v5");
    store.lists().rput("k1", list2);
    Assert.assertEquals(Arrays.asList("v1", "v2", "v3", "v4", "v5"), store.lists().get("k1"));
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testRemove() throws DistkvException {
    KVStore store = new KVStoreImpl();
    store.lists().put("k1", listForKVSTest());
    store.lists().remove("k1", 0);
    Assert.assertEquals(Arrays.asList("v2", "v3"), store.lists().get("k1"));
    store.lists().remove("k1", 1, 2);
    Assert.assertEquals(Collections.singletonList("v2"), store.lists().get("k1"));
    //test exceptions
    store.lists().remove("-k", 0);
  }

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testMRemove() throws DistkvException {
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
    store.lists().mremove("-k", list);
  }

}
