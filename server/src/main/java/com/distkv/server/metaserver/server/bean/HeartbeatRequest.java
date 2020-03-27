package com.distkv.server.metaserver.server.bean;

import com.distkv.common.NodeInfo;

import java.io.Serializable;

public class HeartbeatRequest implements Serializable {
  private static final long serialVersionUID = -4220117786727146673L;

  NodeInfo nodeInfo;

  public HeartbeatRequest(NodeInfo nodeInfo) {
    this.nodeInfo = nodeInfo;
  }

  public NodeInfo getNodeInfo() {
    return nodeInfo;
  }
}
