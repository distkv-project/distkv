package com.distkv.server;

import com.distkv.drpc.DrpcServer;
import com.distkv.drpc.config.ServerConfig;
import com.distkv.rpc.service.DistKVDictService;
import com.distkv.rpc.service.DistKVListService;
import com.distkv.rpc.service.DistKVSetService;
import com.distkv.rpc.service.DistKVSortedListService;
import com.distkv.rpc.service.DistKVStringService;
import com.distkv.server.runtime.DistKVRuntime;
import com.distkv.server.service.DistKVDictServiceImpl;
import com.distkv.server.service.DistKVListServiceImpl;
import com.distkv.server.service.DistKVSetServiceImpl;
import com.distkv.server.service.DistKVSortedListServiceImpl;
import com.distkv.server.service.DistKVStringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// http://patorjk.com/software/taag/#p=display&f=3D%20Diagonal&t=Distkv

public class DstServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstServer.class);

  private DrpcServer drpcServer;

  private DistKVRuntime runtime;

  private DistKVServerConfig config;

  private static String WELCOME_WORDS =
      "    ,---,                           ___          ,-.           \n" +
      "  .'  .' `\\    ,--,               ,--.'|_    ,--/ /|           \n" +
      ",---.'     \\ ,--.'|               |  | :,' ,--. :/ |           \n" +
      "|   |  .`\\  ||  |,      .--.--.   :  : ' : :  : ' /      .---. \n" +
      ":   : |  '  |`--'_     /  /    '.;__,'  /  |  '  /     /.  ./| \n" +
      "|   ' '  ;  :,' ,'|   |  :  /`./|  |   |   '  |  :   .-' . ' | \n" +
      "'   | ;  .  |'  | |   |  :  ;_  :__,'| :   |  |   \\ /___/ \\: | \n" +
      "|   | :  |  '|  | :    \\  \\    `. '  : |__ '  : |. \\.   \\  ' . \n" +
      "'   : | /  ; '  : |__   `----.   \\|  | '.'||  | ' \\ \\\\   \\   ' \n" +
      "|   | '` ,/  |  | '.'| /  /`--'  /;  :    ;'  : |--'  \\   \\    \n" +
      ";   :  .'    ;  :    ;'--'.     / |  ,   / ;  |,'      \\   \\ | \n" +
      "|   ,.'      |  ,   /   `--'---'   ---`-'  '--'         '---\"  \n" +
      "'---'         ---`-'                                           ";

  public DstServer(DistKVServerConfig config) {
    this.config = config;
    ServerConfig config1 = ServerConfig.builder()
        .enableIOThreadOnly(config.enableIOThreadOnly())
        .port(config.getPort())
        .build();
    drpcServer = new DrpcServer(config1);
    runtime = new DistKVRuntime(config);
    registerAllRpcServices();
  }

  public void run() {
    drpcServer.run();
    LOGGER.info("Succeeded to start dst server on port {}.", config.getPort());
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
        DistKVStringService.class, new DistKVStringServiceImpl(this.runtime));
    drpcServer.registerService(
        DistKVListService.class, new DistKVListServiceImpl(this.runtime));
    drpcServer.registerService(
        DistKVSetService.class, new DistKVSetServiceImpl(this.runtime));
    drpcServer.registerService(
        DistKVDictService.class, new DistKVDictServiceImpl(this.runtime));
    drpcServer.registerService(
        DistKVSortedListService.class, new DistKVSortedListServiceImpl(this.runtime));
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

    DistKVServerConfig config = DistKVServerConfig.create();
    if (listeningPort > 0) {
      config.setPort(listeningPort);
    }
    DstServer dstServer = new DstServer(config);
    System.out.println(WELCOME_WORDS);
    dstServer.run();
  }
}
