package com.distkv.server.metaserver.server.bean;

import com.distkv.common.NodeInfo;

import java.io.Serializable;

public class HeartBeatRequest implements Serializable {
  private static final long serialVersionUID = -4220117786727146673L;

  NodeInfo nodeInfo;

  public HeartBeatRequest(NodeInfo nodeInfo) {
    this.nodeInfo = nodeInfo;
  }

  public NodeInfo getNodeInfo() {
    return nodeInfo;
  }
}
