package com.distkv.server.storeserver;

import com.distkv.rpc.service.DistkvService;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.services.DistkvServiceImpl;
import org.drpc.DrpcServer;
import org.drpc.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreServer {

  private static final Logger LOG = LoggerFactory.getLogger(StoreServer.class);

  private DrpcServer drpcServer;

  private StoreRuntime storeRuntime;

  private StoreConfig storeConfig;

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

  public StoreServer(StoreConfig storeConfig) {
    this.storeConfig = storeConfig;
    ServerConfig drpcServerConfig = ServerConfig.builder()
        /// Note: This is a very important flag for `StoreServer` because it
        /// affects the threading model of `StoreServer`.
        /// For a `StoreServer`, it should have the rigorous threading model
        /// for the performance requirements. `Drpc` RPC has its own threading
        /// model with multiple worker threads, and `StoreServer` should have
        /// its own threading model with multiple threads as well. Therefore, it's
        /// hard to manage so many threads to meet our performance requirements if
        /// we don't enable this flag `enableIOThreadOnly`.
        .enableIOThreadOnly(true)
        .port(storeConfig.getPort())
        .build();
    drpcServer = new DrpcServer(drpcServerConfig);
    storeRuntime = new StoreRuntime(storeConfig);
    registerAllRpcServices();
  }

  public void run() {
    try {
      drpcServer.run();
    } catch (Throwable e) {
      LOG.error("Failed with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOG.info("Succeeded to start distkv server on port {}.", storeConfig.getPort());
  }

  private void registerAllRpcServices() {
    drpcServer.registerService(
        DistkvService.class, new DistkvServiceImpl(this.storeRuntime));
  }

  public void shutdown() {
    try {
      drpcServer.stop();
    } catch (Throwable e) {
      LOG.error("Failed shutdown DistkvServer with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOG.debug("Succeeded to shutdown DistkvServer.");
  }

  public static void main(String[] args) {

    int listeningPort = -1;
    if (args.length == 1) {
      try {
        listeningPort = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        LOG.error("Failed to start distkv server, because the port is incorrect format: {}",
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
