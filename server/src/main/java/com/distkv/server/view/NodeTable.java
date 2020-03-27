package com.distkv.server.view;

import com.distkv.common.NodeInfo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class NodeTable {
  /**
   * This map contains the info of all nodes in a group
   */
  private ConcurrentHashMap<String, NodeInfo> nodeTable;

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
  }

  public void put(NodeInfo e) {
    if (nodeTable.isEmpty()) {
      e.getNodeId().setMaster(true);
    }
    nodeTable.put(e.getAddress(), e);
  }

  public HashMap<String, NodeInfo> getMap() {
    return new HashMap<String, NodeInfo>(nodeTable);
  }

  public boolean isEmpty() {
    return nodeTable.isEmpty();
  }
}
