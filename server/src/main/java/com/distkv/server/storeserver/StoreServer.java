package com.distkv.server.storeserver;

import com.distkv.server.AbstractCompositeService;
import com.distkv.server.Address;
import com.distkv.server.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreServer extends AbstractCompositeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreServer.class);

  // used by meta server.
  private Node node;
  private int port;
  private StoreService storeService;

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

  //  TODO(meijie) read the configuration from configure file.
  public StoreServer(int port) {
    super("Store Service");
    this.port = port;
  }

  @Override
  public void serviceInit() {
    node = new Node.NodeBuilder()
        .withNodeAddress(Address.from(port))
        .build();

    StoreConfig storeConfig = StoreConfig.create();
    // TODO simple it.
    if (port > 0) {
      storeConfig.setPort(port);
    }
    storeService = new StoreService(storeConfig);
    addService(storeService);
  }

  @Override
  protected void serviceRun() {
    // do nothing now.
  }

  @Override
  protected void serviceStop() {
    // do nothing now.
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

    StoreServer storeServer = new StoreServer(listeningPort);
    storeServer.config();
    System.out.println(WELCOME_WORDS);
    storeServer.run();
  }
}
