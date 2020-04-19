package com.distkv.server.metaserver.server.bean;

import com.distkv.common.NodeInfo;

import java.io.Serializable;
import java.util.HashMap;

public class HeartbeatResponse extends BaseResponse implements Serializable {
  private static final long serialVersionUID = -4220017786727146673L;

  private HashMap<String, NodeInfo> nodeTable;

  public HashMap<String, NodeInfo> getNodeTable() {
    return nodeTable;
  }

  public void setNodeTable(HashMap<String, NodeInfo> nodeTable) {
    this.nodeTable = nodeTable;
  }
}
