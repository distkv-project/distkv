package test.org.dst.rpc;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import com.google.common.collect.ImmutableList;
import org.dst.server.service.DstStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);

  private static final String SUFFIX_JAR_DIR = "server" + File.separator + "target"
          + File.separator + "dst-server-1.0-SNAPSHOT-jar-with-dependencies.jar";

  private static Process rpcServerProcess = null;

  private static RpcClient client;
  /**
   * @param command the command to start rpc server using a new process.
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
      rpcServerProcess.destroy();
      throw new RuntimeException("Error executing command " + String.join(" ", command), e);
    }
  }

  public static void startRpcServer() {
    final File userDir = new File(System.getProperty("user.dir"));
    final String jarDir = userDir.getParent() + File.separator + SUFFIX_JAR_DIR;
    final List<String> startCommand = ImmutableList.of(
            "java",
            "-classpath",
            jarDir,
            "org.dst.server.service.DstRpcServer"
    );
    executeCommand(startCommand);
  }

  public static void stopRpcServer() {
    rpcServerProcess.destroy();
  }

  public static <T> T openConnection(Class classz){
    startRpcServer();
    RpcClientOptions options = new RpcClientOptions();
    options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    options.setWriteTimeoutMillis(1000);
    options.setReadTimeoutMillis(1000);
    options.setMaxTotalConnections(1000);
    options.setMinIdleConnections(10);
    final String url = "list://127.0.0.1:8082";
    client = new RpcClient(url, options);
    return BrpcProxy.getProxy(client, classz);
  }

  public static void closeConnection(){
    client.stop();
    TestUtil.stopRpcServer();
  }
}


