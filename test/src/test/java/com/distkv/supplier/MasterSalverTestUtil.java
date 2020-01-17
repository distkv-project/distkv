package com.distkv.supplier;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MasterSalverTestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(MasterSalverTestUtil.class);

  private static final String SERVER_SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "dst-server-0.1.3-SNAPSHOT-jar-with-dependencies.jar";
  private static final int NODE_NUM = 3;

  private static Process[] processes = new Process[NODE_NUM];

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  public static void startAllProcess() {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().indexOf("test") != -1) {
      jarDir = userDir.getParent() + File.separator + SERVER_SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SERVER_SUFFIX_JAR_DIR;
    }

    String confPath = userDir.getParent() + File.separator + "conf" + File.separator;
    for (int i = 0; i < NODE_NUM; i++) {
      final List<String> startCommand = ImmutableList.of(
          "java",
          "-Ddst.config=" + confPath + "dst" + (i + 1) + ".conf",
          "-classpath",
          jarDir,
          "com.distkv.dst.server.DstServer"
      );
      int finalI = i;
      new Thread(() -> processes[finalI] = TestUtil.executeCommand(startCommand)).start();
    }
  }

  public static void stopAllProcess() {
    for (int i = 0; i < NODE_NUM; i++) {
      int finalI = i;
      new Thread(() -> stopAServerProcess(processes[finalI])).start();
    }
  }

  //Stop a dmeta server process
  public static void stopAServerProcess(Process process) {
    int numAttempts = 0;
    while (process.isAlive()) {
      if (numAttempts > 0) {
        LOGGER.warn("Attempting to kill rpc server, numAttempts={}.", numAttempts);
      }
      if (numAttempts == 0) {
        process.destroy();
      } else {
        process.destroyForcibly();
      }
      ++numAttempts;
      try {
        process.waitFor(KILL_PROCESS_WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        LOGGER.error("Failed to stop Dmeta server process. This process is exiting.");
        System.exit(-1);
      }
    }

  }
}
