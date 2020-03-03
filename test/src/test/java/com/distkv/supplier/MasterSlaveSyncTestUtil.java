package com.distkv.supplier;

import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.StoreServer;
import com.sun.xml.internal.bind.v2.TODO;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class MasterSlaveSyncTestUtil {

  private static final String KEY_PROPERTY = "distkv.store.config";

  private static CopyOnWriteArrayList<StoreServer> storeServers = new CopyOnWriteArrayList<>();

  private static final int NODE_NUM = 3;

  public static void startAllProcess() {
    final File userDir = new File(System.getProperty("user.dir"));
    String confPath = userDir.getParent() + File.separator + "test" +
        File.separator + "conf" + File.separator;
    for (int i = 0; i < NODE_NUM - 1; i++) {
      System.setProperty(KEY_PROPERTY, confPath + "slave_store_" + (i + 1) + ".conf");
      StoreConfig config = StoreConfig.create();
      StoreServer storeServer = new StoreServer(config);
      storeServer.run();
      storeServers.add(storeServer);
    }

    System.setProperty(KEY_PROPERTY, confPath + "master_store.conf");
    StoreConfig config = StoreConfig.create();
    StoreServer storeServer = new StoreServer(config);
    storeServer.run();
    storeServers.add(storeServer);
  }

  public static void stopAllProcess() {
    for (int i = 0; i < NODE_NUM; i++) {
      storeServers.get(i).shutdown();
    }
  }
}
