package com.distkv.core.operator;

import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
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
