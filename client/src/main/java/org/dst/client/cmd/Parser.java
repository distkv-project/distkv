package org.dst.client.cmd;

import org.dst.utils.StringUtil;

public class Parser {

  private DstCommandWithType dstCommandWithType = new DstCommandWithType();

  public Parser() {
  }

  /**
   * @param line put k1 v1 or str.put k1 v1 or list.put k1 v1 v2 v3 etc.
   * @return return the specific operation type, for example STRING SET LIST etc
   */
  public DstCommandWithType parse(String line) {
    if (StringUtil.isNullOrEmpty(line)) {
      return dstCommandWithType;
    }
    parseOperationType(line);
    if (dstCommandWithType.operationType != DstOperationType.UNKNOWN) {
      parseCommand(line);
    }
    return dstCommandWithType;
  }

  private void parseOperationType(String line) {
    String[] lineArr = line.split(" ");
    DstOperationType type;
    String[] typeArr;

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

    dstCommandWithType.operationType = type;
  }

  /**
   * @return [put, k1, v1] or [put, k1, v1, v2, v3]
   * @line put k1 v1 or str.put k1 v1 or list.put k1 v1 v2 v3 etc.
   */
  private void parseCommand(String line) throws ArrayIndexOutOfBoundsException {
    String[] lineArr = line.split(" ");
    if (lineArr[0].contains(".")) {
      String[] tempArr = lineArr[0].split("\\.");
      //str. list.
      if (tempArr.length == 2) {
        lineArr[0] = tempArr[1];
      }
    }

    dstCommandWithType.command = lineArr;
  }

}
