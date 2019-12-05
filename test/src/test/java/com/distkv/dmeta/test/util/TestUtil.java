package com.distkv.dmeta.test.util;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);

  private static final String SERVER_SUFFIX_JAR_DIR = "server" + File.separator + "target"
      + File.separator + "dmeta-server-1.0-SNAPSHOT-jar-with-dependencies.jar";
  private static final int NODE_NUM = 3;
  private static Process[] nodeProcess = new Process[NODE_NUM];

  /**
   * @param command The command that will be executed.
   */
  private static void executeCommand(List<String> command, int node) {
    try {
      ProcessBuilder processBuilder = new ProcessBuilder(command)
          .redirectOutput(ProcessBuilder.Redirect.INHERIT)
          .redirectError(ProcessBuilder.Redirect.INHERIT)
          .redirectErrorStream(true);
      LOGGER.debug("Executing command: {}", String.join(" ", command));
      nodeProcess[node] = processBuilder.start();
      // TODO(qwang): Refine this wait
      nodeProcess[node].waitFor(1, TimeUnit.SECONDS);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      nodeProcess[node].destroy();
      throw new RuntimeException("Error executing command " + String.join(" ", command), e);
    }
  }

  public static void startAllNode() {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir;
    if (userDir.getPath().indexOf("test") != -1) {
      jarDir = userDir.getParent() + File.separator + SERVER_SUFFIX_JAR_DIR;
    } else {
      jarDir = userDir.getPath() + File.separator + SERVER_SUFFIX_JAR_DIR;
    }
    for (int i = 0; i < NODE_NUM; i++) {
      final List<String> startCommand = ImmutableList.of(
          "java",
          "-classpath",
          jarDir,
          "com.distkv.dmeta.server.DmetaServer",
          userDir.getPath() + File.separator + "tmp" + File.separator + "server" + (i + 1),
          "KV",
          "127.0.0.1:808" + (i + 1),
          "127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083"
      );
      System.out.println(startCommand.toString());
      int finalI = i;
      new Thread(() -> executeCommand(startCommand, finalI)).start();
    }
  }

  public static void stopAllNode() {
    for (int i = 0; i < NODE_NUM; i++) {
      nodeProcess[i].destroy();
    }
  }
}


