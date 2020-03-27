package com.distkv.supplier;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

public class DmetaTestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DmetaTestUtil.class);

  private static final String SERVER_SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar";

  private static final int NODE_NUM = 3;

  private static Process[] processes = new Process[NODE_NUM];

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  public static void startAllDmetaProcess() {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().contains("test")) {
      jarDir = userDir.getParent() + File.separator + SERVER_SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SERVER_SUFFIX_JAR_DIR;
    }
    for (int i = 0; i < NODE_NUM; i++) {
      final List<String> startCommand = ImmutableList.of(
          "java",
          "-classpath",
          jarDir,
          "com.distkv.server.metaserver.server.DmetaServer",
          "tmp" + File.separator + "server" + (i + 1),
          "KV",
          "127.0.0.1:808" + (i + 1),
          "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083"
      );
      int finalI = i;
      processes[finalI] = TestUtil.executeCommand(startCommand);
    }
  }

  public static void stopAllDmetaProcess() {
    for (int i = 0; i < NODE_NUM; i++) {
      TestUtil.stopProcess(processes[i]);
    }
  }
}
