package com.distkv.common;

public class NodeInfo {
  /**
   * Whether this node is a master.
   *
   * True if this node is master node, false if this node is a slave node.
   */
  private boolean isMaster;

  public NodeInfo(boolean isMaster) {
    this.isMaster = isMaster;
  }

  public boolean isMaster() {
    return isMaster;
  }
}
