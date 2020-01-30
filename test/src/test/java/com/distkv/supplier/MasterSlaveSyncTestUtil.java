package com.distkv.supplier;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

public class MasterSlaveSyncTestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(MasterSlaveSyncTestUtil.class);

  private static final String SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "distkv-server-0.1.3-SNAPSHOT-jar-with-dependencies.jar";

  private static final int NODE_NUM = 3;

  private static Process[] processes = new Process[NODE_NUM];

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  public static void startAllProcess() {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().indexOf("test") != -1) {
      jarDir = userDir.getParent() + File.separator + SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SUFFIX_JAR_DIR;
    }

    String confPath = userDir.getParent() + File.separator + "test" +
        File.separator + "conf" + File.separator;
    int i;
    for (i = 0; i < NODE_NUM - 1; i++) {
      final List<String> startCommand = ImmutableList.of(
          "java",
          "-Ddistkv.config=" + confPath + "distkv_slave_" + (i + 1) + ".conf",
          "-classpath",
          jarDir,
          "com.distkv.server.DstServer"
      );
      processes[i] = TestUtil.executeCommand(startCommand);
    }

    final List<String> startCommand = ImmutableList.of(
        "java",
        "-Ddistkv.config=" + confPath + "distkv_master.conf",
        "-classpath",
        jarDir,
        "com.distkv.server.DstServer"
    );
    processes[i] = TestUtil.executeCommand(startCommand);
  }

  public static void stopAllProcess() {
    for (int i = 0; i < NODE_NUM; i++) {
      TestUtil.stopProcess(processes[i]);
    }
  }
}
