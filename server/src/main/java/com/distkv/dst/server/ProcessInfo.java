package com.distkv.dst.server;

public class ProcessInfo {

  private boolean isMaster;

  public static ProcessInfo processInfo = new ProcessInfo();

  private ProcessInfo() {}

  public boolean isMaster() {
    return isMaster;
  }

  public void setMaster(boolean master) {
    isMaster = master;
  }
}
