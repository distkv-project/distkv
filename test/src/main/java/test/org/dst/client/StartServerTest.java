package test.org.dst.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StartServerTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(StartServerTest.class);
  private static Process process = null;

  private boolean executeCommand(List<String> command) {

    try {
      LOGGER.info("Executing command: {}", String.join(" ", command));

      ProcessBuilder processBuilder = new ProcessBuilder(command).redirectOutput(ProcessBuilder.Redirect.INHERIT)
              .redirectError(ProcessBuilder.Redirect.INHERIT);
      process = processBuilder.start();
      process.waitFor(10, TimeUnit.SECONDS);
      return process.exitValue() == 0;
    } catch (Exception e) {
      throw new RuntimeException("Error executing command " + String.join(" ", command), e);
    }
  }

  public void start() {
    //get the project root directory
    File file = new File("");
    String path = file.getAbsolutePath();

    int lastIndex = path.lastIndexOf("\\");
    String serverPtah = path.substring(0, lastIndex);

    //get the classpath
    String classPath = serverPtah + "\\server\\target\\dst-server-1.0-SNAPSHOT-jar-with-dependencies.jar";

    final List<String> startCommand = new ArrayList<>();
    startCommand.add("java");
    startCommand.add("-classpath");
    startCommand.add(classPath);
    startCommand.add("org.dst.server.service.DstRpcServer");

    if (!executeCommand(startCommand)) {
      throw new RuntimeException("Couldn't start dst server.");
    }
  }

  public void stop() {
    process.destroy();
  }
}


