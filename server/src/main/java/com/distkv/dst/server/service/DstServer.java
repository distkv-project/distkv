package com.distkv.dst.server.service;

import com.distkv.drpc.DrpcServer;
import com.distkv.drpc.config.ServerConfig;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstStringService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.server.runtime.DstRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstServer.class);

  private static final int LISTENING_PORT = 8082;

  // TODO(qwang): This should be a Dst Runtime.
  private KVStore kvStore;

  private DstRuntime runtime;

  private DstServer() {
    kvStore = new KVStoreImpl();
    runtime = new DstRuntime();
  }

  private KVStore getKvStore() {
    return kvStore;
  }

  private static String WELCOME_WORDS =
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

    DstServer server = new DstServer();

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

    ServerConfig serverConfig = ServerConfig.builder()
          .port(listeningPort)
          .build();

    DrpcServer drpcServer = new DrpcServer(serverConfig);
    drpcServer.registerService(
        DstStringService.class, new DstStringServiceImpl(server.runtime));
    drpcServer.registerService(
        DstListService.class, new DstListServiceImpl(server.getKvStore()));
    drpcServer.registerService(
        DstSetService.class, new DstSetServiceImpl(server.runtime));
    drpcServer.registerService(
        DstDictService.class, new DstDictServiceImpl(server.getKvStore()));
    drpcServer.registerService(
        DstSortedListService.class, new DstSortedListServiceImpl(server.getKvStore()));

    drpcServer.run();

    LOGGER.info("Succeeded to start dst server on port {}.", listeningPort);

    // Print welcome words.
    System.out.println(WELCOME_WORDS);

    // Run Dst server.
    synchronized (DstServer.class) {
      try {
        DstServer.class.wait();
      } catch (Throwable e) {
        LOGGER.error("Failed with the exception: {}", e.toString());
        System.exit(-1);
      }
    }

  }
}
