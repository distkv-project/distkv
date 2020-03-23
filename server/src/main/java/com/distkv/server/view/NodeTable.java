package com.distkv.server.view;

import com.distkv.common.NodeInfo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeTable {
  private ConcurrentHashMap<String, NodeInfo> nodeTable;

  private volatile boolean hasMaster = false;

  private AtomicInteger integer = new AtomicInteger(-1);

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
  }

  public void put(NodeInfo e) {
//    synchronized (this) {
//      if (!hasMaster && e.isMaster()) {
//        hasMaster = true;
//      } else {
//        e.setMaster(false);
//      }
    System.out.println("size=" + nodeTable.size());
    nodeTable.put(e.getNodeName(), e);
    System.out.println("put" + integer.addAndGet(1));
//    }
  }

  public HashMap<String, NodeInfo> getMap() {
    return new HashMap<String, NodeInfo>(nodeTable);
  }

  public boolean isEmpty() {
    return nodeTable.isEmpty();
  }
}
