package com.distkv.server.view;

import com.distkv.common.NodeInfo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeTable {

  AtomicInteger nodeIndex = new AtomicInteger(1);

  /**
   * This map contains the info of all nodes in a group.
   */
  private ConcurrentHashMap<String, NodeInfo> nodeTable;

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
  }

  public void putNodeInfo(NodeInfo nodeInfo) {
    if (nodeTable.isEmpty()) {
      nodeInfo.setMaster(true);
    }
    if (nodeInfo.getNodeId().getIndex() == -1) {
      nodeInfo.getNodeId().setIndex(nodeIndex.addAndGet(1));
    }
    nodeTable.put(nodeInfo.getAddress(), nodeInfo);
  }

  public HashMap<String, NodeInfo> getMap() {
    return new HashMap<String, NodeInfo>(nodeTable);
  }

  public boolean isEmpty() {
    return nodeTable.isEmpty();
  }
}
