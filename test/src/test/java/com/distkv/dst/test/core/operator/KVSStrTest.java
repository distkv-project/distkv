package com.distkv.dst.test.core.operator;

import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KVSStrTest {
  @Test
  public void testStr() {
    KVStore store = new KVStoreImpl();
    store.strs().put("k1", "v1");
    store.strs().put("k2", "v2");
    Assert.assertEquals("v1", store.strs().get("k1"));
    Assert.assertEquals("v2", store.strs().get("k2"));
    Assert.assertTrue(store.strs().drop("k1"));
    Assert.assertNull(store.strs().get("k1"));
  }

}
