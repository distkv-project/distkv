package com.distkv.server.storeserver;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestConf {
  @Test
  public void testDefaultConf() {
    StoreConfig config = StoreConfig.create();
    Assert.assertEquals(config.getPort(), 8082);
    Assert.assertEquals(config.isMaster(), false);
    Assert.assertEquals(config.getSlaveAddresses(), null);
    Assert.assertEquals(config.getShardNum(), 8);
  }
}
