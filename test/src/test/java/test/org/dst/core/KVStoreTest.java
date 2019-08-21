package test.org.dst.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KVStoreTest {

  @Test
  public void testStr() {
    KVStore ks = new KVStoreImpl();
    ks.strs().put("k1", "v1");
    ks.strs().put("k2", "v2");
    Assert.assertEquals("v1", ks.strs().get("k1"));
    Assert.assertEquals("v2", ks.strs().get("k2"));
    Assert.assertTrue(ks.strs().del("k1"));
    Assert.assertNull(ks.strs().get("k1"));
  }

  @Test
  public void testList() {
    KVStore ks = new KVStoreImpl();
    List<String> list = new ArrayList<String>();
    list.add("v1");
    list.add("v2");
    list.add("v3");
    ks.lists().put("k1", list);
    Assert.assertEquals(list, ks.lists().get("k1"));
    Assert.assertEquals("ok",ks.lists().del("k1").toString());
    Assert.assertNull(ks.lists().get("k1"));
  }

  @Test
  public void testSet() {
    KVStore ks = new KVStoreImpl();
    Set<String> set = new HashSet<String>();
    set.add("v1");
    set.add("v2");
    set.add("v3");
    ks.sets().put("k1", set);
    Assert.assertEquals(set, ks.sets().get("k1"));

    Assert.assertTrue(ks.sets().exists("k1", "v3"));
    Assert.assertEquals("ok", ks.sets().del("k1","v1").toString());
    Assert.assertEquals("ok", ks.sets().dropByKey("k1").toString());
    Assert.assertNull(ks.sets().get("k1"));
  }

  @Test
  public void testDict() {
    KVStore ks = new KVStoreImpl();
    Map<String, String> dict = new HashMap<String, String>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");
    ks.dicts().put("k1", dict);
    Assert.assertEquals(dict, ks.dicts().get("k1"));
    Assert.assertTrue(ks.dicts().del("k1"));
    Assert.assertNull(ks.dicts().get("k1"));
  }

  @Test
  public void testTable() {
  }

}
