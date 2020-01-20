package com.distkv.core.operator;

import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import com.distkv.common.exception.KeyNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashSet;
import java.util.Set;

public class KVSSetTest {

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testSet() {
    KVStore store = new KVStoreImpl();
    Set<String> set = new HashSet<>();
    set.add("v1");
    set.add("v2");
    set.add("v3");
    store.sets().put("k1", set);
    Assert.assertEquals(set, store.sets().get("k1"));
    Assert.assertTrue(store.sets().exists("k1", "v3"));
    Assert.assertEquals("ok", store.sets().removeItem("k1", "v1").toString());
    Assert.assertEquals("ok", store.sets().drop("k1").toString());
    Assert.assertNull(store.sets().get("k1"));

    //test exception
    store.sets().exists("k2", "v1");
  }
}
