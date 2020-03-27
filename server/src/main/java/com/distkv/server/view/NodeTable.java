package com.distkv.server.view;

import com.distkv.common.NodeInfo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeTable {
  /**
   * This map contains the info of all nodes in a group
   */

  AtomicInteger nodeIndex = new AtomicInteger(1);

  private ConcurrentHashMap<String, NodeInfo> nodeTable;

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
  }

  public void put(NodeInfo e) {
    if (nodeTable.isEmpty()) {
      e.getNodeId().setMaster(true);
    }
    if (e.getNodeId().getIndex() == -1) {
      e.getNodeId().setIndex(nodeIndex.addAndGet(1));
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
