package com.distkv.supplier;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  private static final String SUFFIX_JAR_DIR = "server" + File.separator + "target"
          + File.separator + "distkv-server-0.1.3-SNAPSHOT-jar-with-dependencies.jar";

  private static Process rpcServerProcess = null;

  /**
   * @param command The command that will be executed.
   */
  private static void executeCommand(List<String> command) {

    try {
      LOGGER.debug("Executing command: {}", String.join(" ", command));

      ProcessBuilder processBuilder = new ProcessBuilder(command)
          .redirectOutput(ProcessBuilder.Redirect.INHERIT)
          .redirectError(ProcessBuilder.Redirect.INHERIT);
      rpcServerProcess = processBuilder.start();
      // TODO(qwang): Refine this wait
      rpcServerProcess.waitFor(1, TimeUnit.SECONDS);
    } catch (Exception e) {
      rpcServerProcess.destroyForcibly();
      throw new RuntimeException("Error executing command " + String.join(" ", command), e);
    }
  }

  public static void startRpcServer(int serverPort) {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().indexOf("test") != -1) {
      jarDir = userDir.getParent() + File.separator + SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SUFFIX_JAR_DIR;
    }
    final List<String> startCommand = ImmutableList.of(
        "java",
        "-classpath",
        jarDir,
        "com.distkv.server.DstServer",
        String.valueOf(serverPort)
    );
    executeCommand(startCommand);
  }

  public static void stopRpcServer() {
    int numAttempts = 0;
    while (rpcServerProcess.isAlive()) {
      if (numAttempts > 0) {
        LOGGER.warn("Attempting to kill rpc server, numAttempts={}.", numAttempts);
      }
      if (numAttempts == 0) {
        rpcServerProcess.destroy();
      } else {
        rpcServerProcess.destroyForcibly();
      }
      ++numAttempts;
      try {
        rpcServerProcess.waitFor(KILL_PROCESS_WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        LOGGER.error("Failed to stop rpc server. This process is exiting.");
        System.exit(-1);
      }
    }

  }

}
