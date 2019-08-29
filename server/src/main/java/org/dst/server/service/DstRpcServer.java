package org.dst.server.service;

import com.baidu.brpc.server.RpcServer;
import com.baidu.brpc.server.RpcServerOptions;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstRpcServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstRpcServer.class);

  private static final int LISTENING_PORT = 8082;

  private static final int RECEIVE_BUFFER_SIZE = 64 * 1024 * 1024;

  private static final int SEND_BUFFER_SIZE = 64 * 1024 * 1024;

  private static final int KEEP_ALIVE_TIME = 20;

  // TODO(qwang): This should be a Dst Runtime.
  private KVStore kvStore;

  private DstRpcServer() {
    kvStore = new KVStoreImpl();
  }

  private KVStore getKvStore() {
    return kvStore;
  }

  private static String WELCOME_WORD =
      "                                   \n" +
      "                                   \n" +
      "    ,---,                  ___     \n" +
      "  .'  .' `\\              ,--.'|_   \n" +
      ",---.'     \\             |  | :,'  \n" +
      "|   |  .`\\  |  .--.--.   :  : ' :  \n" +
      ":   : |  '  | /  /    '.;__,'  /   \n" +
      "|   ' '  ;  :|  :  /`./|  |   |    \n" +
      "'   | ;  .  ||  :  ;_  :__,'| :    \n" +
      "|   | :  |  ' \\  \\    `. '  : |__  \n" +
      "'   : | /  ;   `----.   \\|  | '.'| \n" +
      "|   | '` ,/   /  /`--'  /;  :    ; \n" +
      ";   :  .'    '--'.     / |  ,   /  \n" +
      "|   ,.'        `--'---'   ---`-'   \n" +
      "'---'                              \n" +
      "                                   ";

  public static void main(String[] args) {

    DstRpcServer rpcServer = new DstRpcServer();

    int listeningPort = LISTENING_PORT;

    if (args.length == 1) {
      try {
        listeningPort = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
        LOGGER.error("Failed to start dst server, because the port is incorrect format: {}",
            args[0]);
        System.exit(0);
      }
    }

    RpcServerOptions options = new RpcServerOptions();
    // TODO(qwang): This should be configurable.
    options.setReceiveBufferSize(RECEIVE_BUFFER_SIZE);
    options.setSendBufferSize(SEND_BUFFER_SIZE);
    options.setKeepAliveTime(KEEP_ALIVE_TIME);

    RpcServer server = new RpcServer(listeningPort, options);

    // Register all services to rpc server.
    server.registerService(new DstStringServiceImpl(rpcServer.getKvStore()));
    server.registerService(new DstSetServiceImpl(rpcServer.getKvStore()));
    server.registerService(new DstListServiceImpl(rpcServer.getKvStore()));
    server.registerService(new DstDictServiceImpl(rpcServer.getKvStore()));
    server.start();

    // print welcome word.
    System.out.println(WELCOME_WORD);

    LOGGER.info("Succeeded to start dst server on port {}.", listeningPort);

    // Run server.
    synchronized (DstRpcServer.class) {
      try {
        DstRpcServer.class.wait();
      } catch (Throwable e) {
        LOGGER.error("Failed with the exception: {}", e.toString());
        System.exit(-1);
      }
    }

  }
}
