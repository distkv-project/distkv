package com.distkv.server.storeserver;

import com.distkv.rpc.service.DistkvService;
import com.distkv.server.AbstractCompositeService;
import com.distkv.server.Address;
import com.distkv.server.Node;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.services.DistkvServiceImpl;
import org.dousi.DousiServer;
import org.dousi.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreServer extends AbstractCompositeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreServer.class);

  // used by meta server.
  private Node node;

  private DousiServer dousiServer;

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
    super("Store Service");
    this.storeConfig = storeConfig;
  }

  @Override
  public void serviceInit() {
    ServerConfig dousiServerConfig = ServerConfig.builder()
        /// Note: This is a very important flag for `StoreServer` because it
        /// affects the threading model of `StoreServer`.
        /// For a `StoreServer`, it should have the rigorous threading model
        /// for the performance requirements. `Dousi` RPC has its own threading
        /// model with multiple worker threads, and `StoreServer` should have
        /// its own threading model with multiple threads as well. Therefore, it's
        /// hard to manage so many threads to meet our performance requirements if
        /// we don't enable this flag `enableIOThreadOnly`.
        .enableIOThreadOnly(true)
        .port(storeConfig.getPort())
        .build();

    node = new Node.NodeBuilder()
        .withNodeId("store-server")
        .withAddress(Address.from(storeConfig.getPort()))
        .build();

    dousiServer = new DousiServer(dousiServerConfig);
    storeRuntime = new StoreRuntime(storeConfig);
    registerAllRpcServices();
  }

  @Override
  protected void serviceRun() {
    try {
      dousiServer.run();
    } catch (Throwable e) {
      LOGGER.error("Failed with the exception: {}", e.toString());
      System.exit(-1);
    }
    LOGGER.info("Succeeded to start dst server on port {}.", storeConfig.getPort());
  }

  @Override
  protected void serviceStop() {
    // do nothing know.
  }

  private void registerAllRpcServices() {
    dousiServer.registerService(
        DistkvService.class, new DistkvServiceImpl(this.storeRuntime));
  }

  public static void main(String[] args) {

    int listeningPort = -1;
    if (args.length == 1) {
      try {
        listeningPort = Integer.parseInt(args[0]);
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
    storeServer.config();
    System.out.println(WELCOME_WORDS);
    storeServer.run();
  }
}
