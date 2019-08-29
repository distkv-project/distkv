package org.dst.client.cmd;

import java.util.Scanner;
import org.dst.client.DefaultDstClient;

public class Main {

  private static final String defaultAddress = "list://127.0.0.1:8082";

  private Parser parser = new Parser();

  private ProcessResult processResult = new ProcessResult();

  public static void main(String[] args) {
    //TODO(jyx) Check the server is open or not

    if (args.length == 0) {
      ProcessResult.client = new DefaultDstClient(defaultAddress);
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

      ProcessResult.client = new DefaultDstClient(sb.toString());
    }

    new Main().loop();
  }

  public void loop() {
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("dst-cli>");
      String line = sc.nextLine();
      DstCommandWithType commandWithType = parser.parse(line);
      String result;
      switch (commandWithType.getCommandType()) {
        case STRING:
          result = processResult.getStringResult(commandWithType.getCommand());
          break;
        case LIST:
          result = processResult.getListResult(commandWithType.getCommand());
          break;
        case SET:
          result = processResult.getSetResult(commandWithType.getCommand());
          break;
        case DICT:
          result = processResult.getDictResult(commandWithType.getCommand());
          break;
        case TABLE:
          result = processResult.getTableResult(commandWithType.getCommand());
          break;
        default:
          result = "Unsupport data type";
          break;
      }
      System.out.println("dst-cli>" + result);
    }
  }

}
