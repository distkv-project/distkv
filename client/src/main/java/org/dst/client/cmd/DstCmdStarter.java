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

    String address;
    if (args.length == 0) {
      address = defaultAddress;
    } else {
      address = covertArgsToAddress(args);
    }

    try {
      client = new DefaultDstClient(address);
    } catch (NullPointerException e) {
      System.out.println("can't connect to the server, please check your input.");
      return;
    }

    if (!client.isConnected()) {
      System.out.println("can't connect to the server, please check the status of your server.");
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

  /**
   * @param args the args like [-h,127.0.0.1,-p,8082]
   * @return list://127.0.0.1:8082
   */
  private static String covertArgsToAddress(String[] args) {
    if (args.length != 4) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    if ("-h".equals(args[0])) {
      sb.append("list://");
      sb.append(args[1]);
    } else {
      return null;
    }

    if ("-p".equals(args[2])) {
      sb.append(":");
      sb.append(args[3]);
    } else {
      return null;
    }

    return sb.toString();
  }


}
