package com.distkv.server.storeserver;

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
import org.dousi.DousiServer;
import org.dousi.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreServer.class);

  private DousiServer dousiServer;

  private StoreRuntime storeRuntime;

  private StoreConfig config;

  /// http://patorjk.com/software/taag/#p=display&f=3D%20Diagonal&t=Distkv
  private static final String WELCOME_WORDS =
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

  public StoreServer(StoreConfig config) {
    this.config = config;
    ServerConfig config1 = ServerConfig.builder()
        /// Note: This is a very important flag for `StoreServer` because it
        /// affects the threading model of `StoreServer`.
        /// For a `StoreServer`, it should have the rigorous threading model
        /// for the performance requirements. `Dousi` RPC has its own threading
        /// model with multiple worker threads, and `StoreServer` should have
        /// its own threading model with multiple threads as well. Therefore, it's
        /// hard to manage so many threads to meet our performance requirements if
        /// we don't enable this flag `enableIOThreadOnly`.
        .enableIOThreadOnly(true)
        .port(config.getPort())
        .build();
    dousiServer = new DousiServer(config1);
    storeRuntime = new StoreRuntime(config);
    registerAllRpcServices();
  }

  public void run() {
    try {
      dousiServer.run();
    } catch (Throwable e) {
      LOGGER.error("Failed with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOGGER.info("Succeeded to start dst server on port {}.", config.getPort());
  }

  private void registerAllRpcServices() {
    dousiServer.registerService(
        DistKVStringService.class, new DistKVStringServiceImpl(this.storeRuntime));
    dousiServer.registerService(
        DistKVListService.class, new DistKVListServiceImpl(this.storeRuntime));
    dousiServer.registerService(
        DistKVSetService.class, new DistKVSetServiceImpl(this.storeRuntime));
    dousiServer.registerService(
        DistKVDictService.class, new DistKVDictServiceImpl(this.storeRuntime));
    dousiServer.registerService(
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

    StoreConfig config = StoreConfig.create();
    if (listeningPort > 0) {
      config.setPort(listeningPort);
    }
    StoreServer storeServer = new StoreServer(config);
    System.out.println(WELCOME_WORDS);
    storeServer.run();
  }
}
