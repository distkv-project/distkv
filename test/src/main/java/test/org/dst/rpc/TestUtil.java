package test.org.dst.rpc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);
  private static Process rpcServerProcess = null;

  /**
   * @param command the command to start rpc server using a new process
   * @return Whether the command succeeded.
   */
  private static boolean executeCommand(List<String> command) {

    try {
      LOGGER.debug("Executing command: {}", String.join(" ", command));

      ProcessBuilder processBuilder = new ProcessBuilder(command).redirectOutput(ProcessBuilder.Redirect.INHERIT)
              .redirectError(ProcessBuilder.Redirect.INHERIT);
      rpcServerProcess = processBuilder.start();
      rpcServerProcess.waitFor(2, TimeUnit.SECONDS);
      return rpcServerProcess.exitValue() == 0;
    } catch (Exception e) {
      throw new RuntimeException("Error executing command " + String.join(" ", command), e);
    }
  }

  public static void startRpcServer() {
    //get the project root directory
    String path = System.getProperty("user.dir");

    int lastIndex = path.lastIndexOf("\\");
    String serverPtah = path.substring(0, lastIndex);

    //get the classpath
    String classPath = serverPtah + "\\server\\target\\dst-server-1.0-SNAPSHOT-jar-with-dependencies.jar";

    final List<String> startCommand = ImmutableList.of(
            "java",
            "-classpath",
            classPath,
            "org.dst.server.service.DstRpcServer"
    );

    if (!executeCommand(startCommand)) {
      throw new RuntimeException("Couldn't start dst server.");
    }
  }

  public static void stopRpcServer() {
    rpcServerProcess.destroy();
  }
}


