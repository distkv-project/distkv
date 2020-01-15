package com.distkv.dst.core.operator;

import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KVSStrTest {

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testStr() {
    KVStore store = new KVStoreImpl();
    store.strs().put("k1", "v1");
    store.strs().put("k2", "v2");
    Assert.assertEquals("v1", store.strs().get("k1"));
    Assert.assertEquals("v2", store.strs().get("k2"));
    Assert.assertEquals(Status.OK, store.strs().drop("k1"));
    store.strs().get("k1");
  }

}
