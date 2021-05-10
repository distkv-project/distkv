package com.distkv.supplier;

import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

public class MasterSlaveSyncTestUtil {

  private static final Logger LOG = LoggerFactory.getLogger(MasterSlaveSyncTestUtil.class);

  private static final String SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar";

  private static final int NODE_NUM = 3;

  private static Process[] processes = new Process[NODE_NUM];

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  private static Set<Integer> killedProcessIndexes;

  public static void startAGroupOfStoreServers() {
    killedProcessIndexes = new HashSet<>();
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().contains("test")) {
      jarDir = userDir.getParent() + File.separator + SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SUFFIX_JAR_DIR;
    }

    String confPath = userDir.getParent() + File.separator + "test" +
        File.separator + "conf" + File.separator;

    final List<String> startCommand = ImmutableList.of(
        "java",
        "-Ddistkv.store.config=" + confPath + "master_store.conf",
        "-classpath",
        jarDir,
        "com.distkv.server.storeserver.StoreServer"
    );
    processes[0] = TestUtil.executeCommand(startCommand);

    int i;
    for (i = 1; i < NODE_NUM; i++) {
      final List<String> startCommand1 = ImmutableList.of(
          "java",
          "-Ddistkv.store.config=" + confPath + "slave_store_" + i + ".conf",
          "-classpath",
          jarDir,
          "com.distkv.server.storeserver.StoreServer"
      );
      processes[i] = TestUtil.executeCommand(startCommand1);
    }


  }

  public static void killOneStoreServerRandomly() {
    TestUtil.stopProcess(processes[0]);
    killedProcessIndexes.add(0);
  }

  public static void stopAGroupOfStoreServers() {
    for (int i = 0; i < NODE_NUM; i++) {
      if (!killedProcessIndexes.contains(i)) {
        TestUtil.stopProcess(processes[i]);
      }
    }
  }
}