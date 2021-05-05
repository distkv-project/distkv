package com.distkv.storageengine;

import org.testng.Assert;

public class BasicTest {

  public void testPutAndGet() {
    StorageEngine engine = new StorageEngine();
    engine.put("k1", "v1");
    engine.put("k2", "v2");
    Assert.assertEquals(engine.get("k1"), "v1");
    Assert.assertEquals(engine.get("k2"), "v2");
  }
}
