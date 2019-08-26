package org.dst.client.cmd;

public class CommandParser {

  /**
   * @param args -h x.x.x.x -p xxxx
   */
  public static String parseAddress(String[] args) {
    if (args.length != 4) {
      System.out.println("the parameter is not enough");
    }

    StringBuilder sb = new StringBuilder();
    if ("-h".equals(args[0])) {
      sb.append(args[1]);
    } else {
      System.out.println("the first parameter must be -h");
    }

    if ("-p".equals(args[2])) {
      sb.append(args[3]);
    } else {
      System.out.println("the third parameter must be -p");
    }

    return sb.toString();
  }

  /**
   * @param line put k1 v1 or str.put k1 v1 or list.put k1 v1 v2 v3 etc.
   * @return return the specific operation type, for example STRING SET LIST etc
   */
  public static DstOperationType parseOperationType(String line) {
    String[] lineArr = line.split(" ");
    DstOperationType type;
    String[] typeArr = null;

    if ("put".equals(lineArr[0]) || "get".equals(lineArr[0])) {
      type = DstOperationType.STRING;
    } else {
      typeArr = lineArr[0].split("\\.");

      if ("str".equals(typeArr[0])) {
        type = DstOperationType.STRING;
      } else if ("list".equals(typeArr[0])) {
        type = DstOperationType.LIST;
      } else if ("set".equals(typeArr[0])) {
        type = DstOperationType.SET;
      } else if ("dict".equals(typeArr[0])) {
        type = DstOperationType.DICT;
      } else if ("create".equals(typeArr[0])) {
        type = DstOperationType.TABLE;
      } else {
        type = DstOperationType.UNKNOWN;
      }
    }

    return type;
  }

  /**
   * @return [put, k1, v1] or [put, k1, v1, v2, v3]
   * @line put k1 v1 or str.put k1 v1 or list.put k1 v1 v2 v3 etc.
   */
  public static String[] parseCommand(String line) {
    String[] lineArr = line.split(" ");
    if (lineArr[0].contains(".")) {
      lineArr[0] = lineArr[0].split("\\.")[1];
    }
    return lineArr;
  }
}
