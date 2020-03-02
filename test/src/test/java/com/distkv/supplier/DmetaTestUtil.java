package com.distkv.supplier;

import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.StoreServer;
import java.util.concurrent.CopyOnWriteArrayList;

public class DmetaTestUtil {

  private static final int NODE_NUM = 3;

  private static CopyOnWriteArrayList<StoreServer> storeServers = new CopyOnWriteArrayList<>();

  public static void startAllDmetaProcess() {
    for (int i = 0; i < NODE_NUM; i++) {
      StoreConfig config = StoreConfig.create();
      config.setPort(8081 + i);
      StoreServer storeServer = new StoreServer(config);
      storeServer.run();
      storeServers.add(storeServer);
    }
  }

  public static void stopAllDmetaProcess() {
    for (int i = 0; i < NODE_NUM; i++) {
      storeServers.get(i).shutdown();
    }
  }
}