package com.distkv.common;

public class NodeInfo {
  // NodeId nodeId;
  private boolean isMaster;

  public NodeInfo(boolean isMaster) {
    this.isMaster = isMaster;
  }

  public boolean isMaster() {
    return isMaster;
  }
}
