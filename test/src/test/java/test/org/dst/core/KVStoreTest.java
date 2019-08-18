package test.org.dst.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.dst.exception.KeyNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVStoreTest {

  @Test
  public void testStr() {
    KVStore ks = new KVStoreImpl();
    ks.str().put("k1", "v1");
    ks.str().put("k2", "v2");
    Assertions.assertEquals("v1", ks.str().get("k1"));
    Assertions.assertEquals("v2", ks.str().get("k2"));
    Assertions.assertTrue(ks.str().del("k1"));
    Assertions.assertNull(ks.str().get("k1"));
  }

  @Test
  public void testList() {
    KVStore ks = new KVStoreImpl();
    List<String> list = new ArrayList<String>();
    list.add("v1");
    list.add("v2");
    list.add("v3");
    ks.list().put("k1", list);
    Assertions.assertEquals(list, ks.list().get("k1"));
    Assertions.assertTrue(ks.list().del("k1"));
    Assertions.assertNull(ks.list().get("k1"));
  }

  @Test
  public void testSet() {
    KVStore ks = new KVStoreImpl();
    Set<String> set = new HashSet<String>();
    set.add("v1");
    set.add("v2");
    set.add("v3");
    ks.set().put("k1", set);
    Assertions.assertEquals(set, ks.set().get("k1"));
    try {
      Assertions.assertTrue(ks.set().exists("k1", "v3"));
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    }
    Assertions.assertTrue(ks.set().del("k1"));
    Assertions.assertNull(ks.set().get("k1"));
  }

  @Test
  public void testDict() {
    KVStore ks = new KVStoreImpl();
    Map<String, String> dict = new HashMap<String, String>();
    dict.put("k1", "v1");
    dict.put("k2", "v2");
    dict.put("k3", "v3");
    ks.dict().put("k1", dict);
    Assertions.assertEquals(dict, ks.dict().get("k1"));
    Assertions.assertTrue(ks.dict().del("k1"));
    Assertions.assertNull(ks.dict().get("k1"));
  }

  @Test
  public void testTable() {
  }

}
