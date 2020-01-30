package com.distkv.server.storeserver;

import com.distkv.drpc.DrpcServer;
import com.distkv.drpc.config.ServerConfig;
import com.distkv.rpc.service.DistKVDictService;
import com.distkv.rpc.service.DistKVListService;
import com.distkv.rpc.service.DistKVSetService;
import com.distkv.rpc.service.DistKVSortedListService;
import com.distkv.rpc.service.DistKVStringService;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.services.DistKVDictServiceImpl;
import com.distkv.server.storeserver.services.DistKVListServiceImpl;
import com.distkv.server.storeserver.services.DistKVSetServiceImpl;
import com.distkv.server.storeserver.services.DistKVSortedListServiceImpl;
import com.distkv.server.storeserver.services.DistKVStringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreServer.class);

  private DrpcServer drpcServer;

  private StoreRuntime storeRuntime;

  private StoreServerConfig config;

  /// http://patorjk.com/software/taag/#p=display&f=3D%20Diagonal&t=Distkv
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

  public StoreServer(StoreServerConfig config) {
    this.config = config;
    ServerConfig config1 = ServerConfig.builder()
        .enableIOThreadOnly(true)
        .port(config.getPort())
        .build();
    drpcServer = new DrpcServer(config1);
    storeRuntime = new StoreRuntime(config);
    registerAllRpcServices();
  }

  public void run() {
    drpcServer.run();
    LOGGER.info("Succeeded to start dst server on port {}.", config.getPort());
    synchronized (StoreServer.class) {
      try {
        StoreServer.class.wait();
      } catch (Throwable e) {
        LOGGER.error("Failed with the exception: {}", e.toString());
        System.exit(-1);
      }
    }
  }

  private void registerAllRpcServices() {
    drpcServer.registerService(
        DistKVStringService.class, new DistKVStringServiceImpl(this.storeRuntime));
    drpcServer.registerService(
        DistKVListService.class, new DistKVListServiceImpl(this.storeRuntime));
    drpcServer.registerService(
        DistKVSetService.class, new DistKVSetServiceImpl(this.storeRuntime));
    drpcServer.registerService(
        DistKVDictService.class, new DistKVDictServiceImpl(this.storeRuntime));
    drpcServer.registerService(
        DistKVSortedListService.class, new DistKVSortedListServiceImpl(this.storeRuntime));
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

    StoreServerConfig config = StoreServerConfig.create();
    if (listeningPort > 0) {
      config.setPort(listeningPort);
    }
    StoreServer storeServer = new StoreServer(config);
    System.out.println(WELCOME_WORDS);
    storeServer.run();
  }
}
