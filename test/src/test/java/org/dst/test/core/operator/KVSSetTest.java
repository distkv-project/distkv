package org.dst.test.core.operator;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.common.exception.KeyNotFoundException;
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
    Assert.assertEquals("ok", store.sets().del("k1", "v1").toString());
    Assert.assertEquals("ok", store.sets().dropByKey("k1").toString());
    Assert.assertNull(store.sets().get("k1"));

    //test exception
    store.sets().exists("k2", "v1");
  }
}
