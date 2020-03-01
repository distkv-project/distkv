package com.distkv.server.storeserver;

import com.distkv.server.Service;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StoreServerTest {
  @Test
  public void testStoreServer() {
    StoreConfig config = StoreConfig.create();
    config.setPort(8081);
    StoreServer storeServer = new StoreServer(config);
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.UNINITED);
    storeServer.config();
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.INITED);
    storeServer.run();
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.RUNNING);
    storeServer.stop();
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.STOPPED);
  }
}
