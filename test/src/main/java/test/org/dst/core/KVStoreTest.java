package test.org.dst.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.dst.core.exception.KeyNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class KVStoreTest {

  public Logger initLog(){
    PropertyConfigurator.configure( "./src/log4j.properties" );
    return Logger.getLogger(KVStoreTest. class );
  }

  @Test
  public void testStr() {
    Logger logger = initLog();
    KVStore ks = new KVStoreImpl();
    logger.debug("testStr debug");
    ks.str().put("k1", "v1");
    logger.info("ks.str().put(\"k1\", \"v1\")");
    ks.str().put("k2", "v2");
    logger.info("ks.str().put(\"k2\", \"v2\")");
    Assertions.assertEquals("v1", ks.str().get("k1"));
    Assertions.assertEquals("v2", ks.str().get("k2"));
    Assertions.assertTrue(ks.str().del("k1"));
    Assertions.assertNull(ks.str().get("k1"));
  }

  @Test
  public void testList() {
    Logger logger = initLog();
    KVStore ks = new KVStoreImpl();
    logger.debug("testList debug");
    List<String> list = new ArrayList<String>();
    list.add("v1");
    logger.info("list.add(\"v1\")");
    list.add("v2");
    logger.info("list.add(\"v2\")");
    list.add("v3");
    logger.info("list.add(\"v3\")");
    ks.list().put("k1", list);
    logger.info("ks.list().put(\"k1\", list)");
    Assertions.assertEquals(list, ks.list().get("k1"));
    Assertions.assertTrue(ks.list().del("k1"));
    Assertions.assertNull(ks.list().get("k1"));
  }

  @Test
  public void testSet() {
    Logger logger = initLog();
    KVStore ks = new KVStoreImpl();
    logger.debug("testSet debug");
    Set<String> set = new HashSet<String>();
    set.add("v1");
    logger.info("set.add(\"v1\")");
    set.add("v2");
    logger.info("set.add(\"v2\")");
    set.add("v3");
    logger.info("set.add(\"v3\")");
    ks.set().put("k1", set);
    logger.info("ks.set().put(\"k1\", set)");
    Assertions.assertEquals(set, ks.set().get("k1"));
    try {
      Assertions.assertTrue(ks.set().exists("k1", "v3"));
    } catch (KeyNotFoundException e) {
      logger.error("testSet error");
      e.printStackTrace();
    }
    Assertions.assertTrue(ks.set().del("k1"));
    Assertions.assertNull(ks.set().get("k1"));
  }

  @Test
  public void testDict() {
    Logger logger = initLog();
    KVStore ks = new KVStoreImpl();
    logger.debug("testDict debug");
    Map<String, String> dict = new HashMap<String, String>();
    dict.put("k1", "v1");
    logger.info("dict.put(\"k1\", \"v1\")");
    dict.put("k2", "v2");
    logger.info("dict.put(\"k2\", \"v2\")");
    dict.put("k3", "v3");
    logger.info("dict.put(\"k3\", \"v3\")");
    ks.dict().put("k1", dict);
    logger.info("ks.dict().put(\"k1\", dict)");
    Assertions.assertEquals(dict, ks.dict().get("k1"));
    Assertions.assertTrue(ks.dict().del("k1"));
    Assertions.assertNull(ks.dict().get("k1"));
  }

  @Test
  public void testTable() {
  }

}
