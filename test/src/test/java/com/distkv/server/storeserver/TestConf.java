package com.distkv.server.storeserver;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class TestConf {
  @Test
  public void testDefaultConf() {
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 8082);
    Assert.assertFalse(config.isMaster());
    Assert.assertNull(config.getSlaveAddresses());
    Assert.assertEquals(config.getShardNum(), 8);
  }

  @Test
  public void testOverwriteConf() {
    final File userDir = new File(System.getProperty("user.dir"));
    String confPath = userDir.getParent() + File.separator + "test" +
        File.separator + "conf" + File.separator + "test.store.conf";
    System.setProperty("distkv.store.config", confPath);
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 18082);
    Assert.assertTrue(config.isMaster());
    Assert.assertEquals(config.getSlaveAddresses().size(), 2);
    Assert.assertEquals(config.getShardNum(), 8);
  }
}
