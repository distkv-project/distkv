package com.distkv.supplier;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestUtil {

  private static final Logger LOG = LoggerFactory.getLogger(TestUtil.class);

  private static final int KILL_PROCESS_WAIT_TIMEOUT_SECONDS = 1;

  private static final String SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar";

  private static Process rpcServerProcess = null;

  /**
   * @param command The command that will be executed.
   */
  public static Process executeCommand(List<String> command) {
    Process process = null;
    try {
      ProcessBuilder processBuilder = new ProcessBuilder(command)
          .redirectOutput(ProcessBuilder.Redirect.INHERIT)
          .redirectError(ProcessBuilder.Redirect.INHERIT)
          .redirectErrorStream(true);
      LOG.debug("Executing command: {}", String.join(" ", command));
      process = processBuilder.start();
      process.waitFor(1, TimeUnit.SECONDS);
      return process;
    } catch (Exception e) {
      if (process != null) {
        process.destroyForcibly();
      }
      LOG.error(e.getMessage());
      throw new RuntimeException("Error executing command " + String.join(" ", command), e);
    }
  }

  public static void startRpcServer(int serverPort) {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().contains("test")) {
      jarDir = userDir.getParent() + File.separator + SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SUFFIX_JAR_DIR;
    }
    final List<String> startCommand = ImmutableList.of(
        "java",
        "-classpath",
        jarDir,
        "com.distkv.server.storeserver.StoreServer",
        String.valueOf(serverPort)
    );
    rpcServerProcess = executeCommand(startCommand);
  }

  public static void stopProcess(Process process) {
    int numAttempts = 0;
    while (process.isAlive()) {
      if (numAttempts > 0) {
        LOG.warn("Attempting to kill rpc server, numAttempts={}.", numAttempts);
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
        LOG.error("Failed to stop rpc server. This process is exiting.");
        System.exit(-1);
      }
    }

  }

  public static Process getProcess() {
    return rpcServerProcess;
  }

}
