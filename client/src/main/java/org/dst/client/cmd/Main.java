package org.dst.client.cmd;

import java.util.Scanner;
import org.dst.client.DefaultDstClient;

public class Main {

  private static final String defaultAddress = "list://127.0.0.1:8082";

  public static void main(String[] args) {
    //TODO(jyx) Check the server is open or not

    if (args.length == 0) {
      ProcessResult.client = new DefaultDstClient(defaultAddress);
    } else {
      ProcessResult.client = new DefaultDstClient(CommandParser.parseAddress(args));
    }
    loop();
  }

  public static void loop() {
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("dst-cli>");
      String line = sc.nextLine();
      DstOperationType type = CommandParser.parseOperationType(line);
      String result;
      switch (type) {
        case STRING:
          result = ProcessResult.getStringResult(CommandParser.parseCommand(line));
          break;
        case LIST:
          result = ProcessResult.getListResult(CommandParser.parseCommand(line));
          break;
        case SET:
          result = ProcessResult.getSetResult(CommandParser.parseCommand(line));
          break;
        case DICT:
          result = ProcessResult.getDictResult(CommandParser.parseCommand(line));
          break;
        case TABLE:
          result = ProcessResult.getTableResult(CommandParser.parseCommand(line));
          break;
        default:
          result = "Unsupport data type";
          break;
      }
      System.out.println(result);
    }
  }

}
