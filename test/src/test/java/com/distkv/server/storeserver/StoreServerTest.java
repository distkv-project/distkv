package com.distkv.server.storeserver;

import com.distkv.server.Service;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StoreServerTest {
  @Test
  public void testStoreServer() {
    StoreServer storeServer = new StoreServer(8081);
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.UNINITED);
    storeServer.config();
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.INITED);
    storeServer.run();
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.RUNNING);
    storeServer.stop();
    assertEquals(storeServer.getStatus(), Service.ServiceStatus.STOPPED);
  }
}
