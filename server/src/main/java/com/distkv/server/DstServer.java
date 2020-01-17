package com.distkv.server;

import com.distkv.drpc.DrpcServer;
import com.distkv.drpc.config.ServerConfig;
import com.distkv.rpc.service.DstDictService;
import com.distkv.rpc.service.DstListService;
import com.distkv.rpc.service.DstSetService;
import com.distkv.rpc.service.DstSortedListService;
import com.distkv.rpc.service.DstStringService;
import com.distkv.server.runtime.DstRuntime;
import com.distkv.server.service.DstDictServiceImpl;
import com.distkv.server.service.DstListServiceImpl;
import com.distkv.server.service.DstSetServiceImpl;
import com.distkv.server.service.DstSortedListServiceImpl;
import com.distkv.server.service.DstStringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstServer.class);

  private DrpcServer drpcServer;

  private DstRuntime runtime;

  private DstServerConfig config;

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
    this.config = config;
    ServerConfig config1 = ServerConfig.builder()
        .port(config.getPort())
        .build();
    drpcServer = new DrpcServer(config1);
    runtime = new DstRuntime(config);
    registerAllRpcServices();
  }

  public void run() {
    drpcServer.run();
    LOGGER.error("Succeeded to start dst server on port {}.", config.getPort());
    synchronized (DstServer.class) {
      try {
        DstServer.class.wait();
      } catch (Throwable e) {
        LOGGER.error("Failed with the exception: {}", e.toString());
        System.exit(-1);
      }
    }
  }

  private void registerAllRpcServices() {
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

    int listeningPort = -1;
    if (args.length == 1) {
      try {
        listeningPort = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
        LOGGER.error("Failed to start dst server, because the port is incorrect format: {}",
            args[0]);
        System.exit(0);
      }
    }

    DstServerConfig config = DstServerConfig.create();
    if (listeningPort > 0) {
      config.setPort(listeningPort);
    }
    DstServer dstServer = new DstServer(config);
    System.out.println(WELCOME_WORDS);
    dstServer.run();
  }
}
