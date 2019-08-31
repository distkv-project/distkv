package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import org.dst.client.DefaultDstClient;

public class Main {

  private static final String defaultAddress = "list://127.0.0.1:8082";

  private Parser parser = new Parser();

  private static ProcessResult processResult = new ProcessResult();

  private HashMap<DstOperationType, Function<DstCommandWithType, ClientResult>> commandHandlers = new HashMap<>();

  public static void main(String[] args) {
    //TODO(jyx) Check the server is open or not

    if (args.length == 0) {
      processResult.client = new DefaultDstClient(defaultAddress);
    } else {
      if (args.length != 4) {
        System.out.println("the parameter is not enough");
      }

      StringBuilder sb = new StringBuilder();
      if ("-h".equals(args[0])) {
        sb.append("list://");
        sb.append(args[1]);
      } else {
        System.out.println("the first parameter must be -h");
      }

      if ("-p".equals(args[2])) {
        sb.append(":");
        sb.append(args[3]);
      } else {
        System.out.println("the third parameter must be -p");
      }
      processResult.client = new DefaultDstClient(sb.toString());
    }
    new Main().loop();
  }

  private void loop() {
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("dst-cli>");
      String line = sc.nextLine();
      DstCommandWithType commandWithType = parser.parse(line);
      ClientResult clientResult = executeCommand(commandWithType);
      System.out.println("dst-cli>" + clientResult);
    }
  }

  private ClientResult executeCommand(DstCommandWithType commandWithType) {
    commandHandlers.put(commandWithType.getCommandType(), processResult);
    return commandHandlers.get(commandWithType.getCommandType()).apply(commandWithType);
  }
}
