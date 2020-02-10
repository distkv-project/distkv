package com.distkv.server.storeserver;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class TestConf {
  @Test
  public void testDefaultConf() {
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 8082);
    Assert.assertEquals(config.isMaster(), false);
    Assert.assertEquals(config.getSlaveAddresses(), null);
    Assert.assertEquals(config.getShardNum(), 8);
  }

  @Test
  public void testOverwriteConf() {
    final File userDir = new File(System.getProperty("user.dir"));
    String confPath = userDir.getParent() + File.separator + "test" +
        File.separator + "conf" + File.separator + "master_store.conf";
    System.setProperty("distkv.store.config",confPath);
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 18082);
    Assert.assertEquals(config.isMaster(), true);
    Assert.assertEquals(config.getSlaveAddresses().size(), 2);
    Assert.assertEquals(config.getShardNum(), 8);

  }
}
