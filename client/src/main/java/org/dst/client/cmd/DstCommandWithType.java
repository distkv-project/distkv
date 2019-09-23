package org.dst.client.cmd;

public class DstCommandWithType {

  public DstOperationType operationType = DstOperationType.UNKNOWN;

  public String[] command;

  public DstCommandWithType() {}

  public DstOperationType getCommandType() {
    return this.operationType;
  }

  public String[] getCommand() {
    return this.command;
  }

}
