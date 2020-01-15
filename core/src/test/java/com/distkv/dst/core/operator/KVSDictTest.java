package com.distkv.dst.core.operator;

import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.utils.Status;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class KVSDictTest {

  @Test(expectedExceptions = KeyNotFoundException.class)
  public void testDict() {
    KVStore store = new KVStoreImpl();
    Map<String, String> dict = new HashMap<String, String>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");
    store.dicts().put("k1", dict);
    Assert.assertEquals(dict, store.dicts().get("k1"));
    Assert.assertEquals(Status.OK, store.dicts().drop("k1"));
    store.dicts().get("k1");
  }
}
