package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import org.dst.client.DefaultDstClient;

public class DstCmdStarter {

  private static final String defaultAddress = "list://127.0.0.1:8082";

  private static DefaultDstClient client;

  private static HashMap<DstOperationType, Function<DstCommandWithType, ClientResult>>
          commandHandlers = new HashMap<>();

  public static void main(String[] args) {
    //TODO(jyx) Check the server is open or not

    if (args.length == 0) {
      client = new DefaultDstClient(defaultAddress);
    }
    //TODO(jyx) deal with -h 127.0.0.1 -p 8082

    //register different operation type handler
    commandHandlers.put(DstOperationType.STRING, new StringHandler(client));
    commandHandlers.put(DstOperationType.SET, new SetHandler(client));
    commandHandlers.put(DstOperationType.LIST, new ListHandler(client));
    commandHandlers.put(DstOperationType.DICT, new DictHandler(client));
    commandHandlers.put(DstOperationType.TABLE, new TableHandler(client));
    commandHandlers.put(DstOperationType.UNKNOWN, new UnkonwnHandler(client));
    new DstCmdStarter().loop();
  }

  private void loop() {
    Parser parser = new Parser();
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("dst-cli> ");
      String line = sc.nextLine();
      DstCommandWithType commandWithType = parser.parse(line);
      ClientResult clientResult = executeCommand(commandWithType);
      System.out.println("dst-cli> " + clientResult);
    }
  }

  private ClientResult executeCommand(DstCommandWithType commandWithType) {
    return commandHandlers.get(commandWithType.operationType).apply(commandWithType);
  }
}
