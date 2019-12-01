package com.distkv.dst.server.service;

import com.distkv.drpc.Exporter;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstStringService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstRpcServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstRpcServer.class);

  private static final int LISTENING_PORT = 8082;

  // TODO(qwang): This should be a Dst Runtime.
  private KVStore kvStore;

  private DstRpcServer() {
    kvStore = new KVStoreImpl();
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

    // TODO(qwang): Rename exporter to DrcpServer.
    Exporter exporter = new Exporter();
    // TODO(qwang): Refine this protocol name.
    exporter.setProtocol("dst");
    exporter.registerService(
        DstStringService.class, new DstStringServiceImpl(rpcServer.getKvStore()));
    exporter.registerService(
        DstListService.class, new DstListServiceImpl(rpcServer.getKvStore()));
    exporter.registerService(
        DstSetService.class, new DstSetServiceImpl(rpcServer.getKvStore()));
    exporter.registerService(
        DstDictService.class, new DstDictServiceImpl(rpcServer.getKvStore()));
    exporter.registerService(
        DstSortedListService.class, new DstSortedListServiceImpl(rpcServer.getKvStore()));
    exporter.isLocal(false);
    exporter.setPort(listeningPort);

    // TODO(qwang): Rename this to drpcServer.run();
    exporter.export();

    LOGGER.info("Succeeded to start dst server on port {}.", listeningPort);

    // Print welcome words.
    System.out.println(WELCOME_WORDS);

    // Run Dst server.
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
