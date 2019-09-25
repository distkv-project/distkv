package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import org.dst.client.DefaultDstClient;
import org.dst.common.exception.DstException;

public class DstCmdStarter {

  private static final String defaultAddress = "list://127.0.0.1:8082";

  private static DefaultDstClient client;

  private static HashMap<DstOperationType, Function<DstCommandWithType, ClientResult>>
          commandHandlers = new HashMap<>();

  public static void main(String[] args) {

    String address;
    try {
      address = ArgsParseUtil.covertArgsToAddress(args);
    } catch (DstException e) {
      System.out.println(e.getMessage());
      return;
    }

    try {
      client = new DefaultDstClient(address);
    } catch (Exception e) {
      System.out.println("connect failure，please check your input.");
      return;
  }

    if (!client.isConnected()) {
      System.out.println("dst-server connect failure, please check the status of your server.");
      return;
    }

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
