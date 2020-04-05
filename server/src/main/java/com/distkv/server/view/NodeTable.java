package com.distkv.server.view;

import com.distkv.common.NodeInfo;
import com.distkv.common.NodeStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeTable implements Serializable {

  private static final long serialVersionUID = -4220017786727046673L;

  AtomicInteger nodeIndex = new AtomicInteger(1);

  /**
   * This map contains the info of all nodes in a group.
   */
  private ConcurrentHashMap<String, NodeInfo> nodeTable;

  /**
   * The interval time of heartbeat.
   */
  public static int HEARTBEAT_INTERVAL = 3000;

  /**
   * The delay time of next heartbeat.
   */
  public static int HEARTBEAT_INTERVAL_DELAY = 500;

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
  }

  public void putNodeInfo(NodeInfo nodeInfo) {

    long lastTimeHeartbeat;

    if (nodeTable.isEmpty()) {
      nodeInfo.setIsMaster(true);
    }
    if (nodeInfo.getNodeId().getIndex() == -1) {
      nodeInfo.getNodeId().setIndex(nodeIndex.addAndGet(1));
    }
    nodeInfo.setLastTimeHeartbeat(lastTimeHeartbeat = System.currentTimeMillis());
    nodeTable.put(nodeInfo.getAddress(), nodeInfo);

    // Wait next time heartbeat.
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        NodeInfo checkInfo = nodeTable.get(nodeInfo.getAddress());
        if (checkInfo.getLastTimeHeartbeat() == lastTimeHeartbeat) {
          checkInfo.setStatus(NodeStatus.DEAD);
          nodeTable.put(checkInfo.getAddress(), checkInfo);
        }
      }
    }, HEARTBEAT_INTERVAL + HEARTBEAT_INTERVAL_DELAY);
  }

  public HashMap<String, NodeInfo> getMap() {
    return new HashMap<String, NodeInfo>(nodeTable);
  }

  public boolean isEmpty() {
    return nodeTable.isEmpty();
  }
}
