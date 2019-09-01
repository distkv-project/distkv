package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import org.dst.client.DefaultDstClient;

public class Main {

  private static final String defaultAddress = "list://127.0.0.1:8082";

  private Parser parser = new Parser();

  private static ProcessResult processResult = new ProcessResult();

  private HashMap<DstOperationType, Function<DstCommandWithType, ClientResult>>
          commandHandlers = new HashMap<>();

  public static void main(String[] args) {
    //TODO(jyx) Check the server is open or not

    if (args.length == 0) {
      processResult.client = new DefaultDstClient(defaultAddress);
    } else { //TODO(jyx) deal with -h 127.0.0.1 -p 8082

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
