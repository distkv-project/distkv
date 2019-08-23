package test.org.dst.core.operator;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class KVSDictTest {
  @Test
  public void testDict() {
    KVStore store = new KVStoreImpl();
    Map<String, String> dict = new HashMap<String, String>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");
    store.dicts().put("k1", dict);
    Assert.assertEquals(dict, store.dicts().get("k1"));
    Assert.assertTrue(store.dicts().del("k1"));
    Assert.assertNull(store.dicts().get("k1"));
  }
}
