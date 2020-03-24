package com.distkv.server.view;

import com.distkv.common.NodeInfo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class NodeTable {
  private ConcurrentHashMap<String, NodeInfo> nodeTable;

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
  }

  public void put(NodeInfo e) {
    System.out.println("hhh");
    if (nodeTable.isEmpty()) {
      e.setMaster(true);
    }
    nodeTable.put(e.getNodeName(), e);
  }

  public HashMap<String, NodeInfo> getMap() {
    return new HashMap<String, NodeInfo>(nodeTable);
  }

  public boolean isEmpty() {
    return nodeTable.isEmpty();
  }
}
