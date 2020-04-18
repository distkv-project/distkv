package com.distkv.server.storeserver;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;

@Test(singleThreaded = true)
public class TestConf {
  @Test
  public void testDefaultConf() {
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 8082);
    Assert.assertEquals(config.getShardNum(), 8);
    Assert.assertEquals(config.getMode(), RunningMode.STANDALONE);
    Assert.assertEquals(config.getMetaServerAddresses(),
        "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
  }

  @Test
  public void testOverwriteConf() {
    final File userDir = new File(System.getProperty("user.dir"));
    String confPath = userDir.getParent() + File.separator + "test" +
        File.separator + "conf" + File.separator + "test.store.conf";
    System.setProperty("distkv.store.config", confPath);
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 18082);
    Assert.assertEquals(config.getShardNum(), 8);
  }
}
