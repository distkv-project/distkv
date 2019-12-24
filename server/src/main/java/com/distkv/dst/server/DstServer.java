package com.distkv.dst.server;

import com.distkv.drpc.DrpcServer;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.rpc.service.DstStringService;
import com.distkv.dst.server.runtime.DstRuntime;
import com.distkv.dst.server.service.DstDictServiceImpl;
import com.distkv.dst.server.service.DstListServiceImpl;
import com.distkv.dst.server.service.DstSetServiceImpl;
import com.distkv.dst.server.service.DstSortedListServiceImpl;
import com.distkv.dst.server.service.DstStringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstServer.class);

  private boolean isMaster;

  private DrpcServer drpcServer;

  private DstRuntime runtime;

  public static int listeningPort = 8082;

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

  public DstServer(DstServerConfig config) {
    drpcServer = new DrpcServer(config.genRpcConfig());
    listeningPort = config.getPort();
    isMaster = config.isMaster();
    runtime = new DstRuntime();
    initRpc();
  }

  public void run() {
    drpcServer.run();
    LOGGER.error("Succeeded to start dst server on port {}.", listeningPort);
    synchronized (DstServer.class) {
      try {
        DstServer.class.wait();
      } catch (Throwable e) {
        LOGGER.error("Failed with the exception: {}", e.toString());
        System.exit(-1);
      }
    }
  }

  private void initRpc() {
    drpcServer.registerService(
        DstStringService.class, new DstStringServiceImpl(this.runtime));
    drpcServer.registerService(
        DstListService.class, new DstListServiceImpl(this.runtime));
    drpcServer.registerService(
        DstSetService.class, new DstSetServiceImpl(this.runtime));
    drpcServer.registerService(
        DstDictService.class, new DstDictServiceImpl(this.runtime));
    drpcServer.registerService(
        DstSortedListService.class, new DstSortedListServiceImpl(this.runtime));
  }

  public static void main(String[] args) {
    if (args.length == 1) {
      try {
        listeningPort = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
        LOGGER.error("Failed to start dst server, because the port is incorrect format: {}",
            args[0]);
        System.exit(0);
      }
    }
    DstServerConfig.DstServerConfigBuilder builder = DstServerConfig.builder();
    DstServerConfig config = builder
        .isMaster(true)
        .port(listeningPort)
        .build();
    DstServer dstServer = new DstServer(config);
    System.out.println(WELCOME_WORDS);
    dstServer.run();
  }
}
