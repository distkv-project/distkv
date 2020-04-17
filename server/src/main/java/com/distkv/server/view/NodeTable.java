package com.distkv.server.view;

import com.distkv.common.Constants;
import com.distkv.common.NodeInfo;
import com.distkv.common.NodeState;

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
    nodeInfo.setLastHeartbeatTimestamp(lastTimeHeartbeat = System.currentTimeMillis());
    nodeTable.put(nodeInfo.getAddress(), nodeInfo);

    // Wait next time heartbeat.
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        NodeInfo checkInfo = nodeTable.get(nodeInfo.getAddress());
        if (checkInfo.getLastHeartbeatTimestamp() == lastTimeHeartbeat) {
          checkInfo.setState(NodeState.DEAD);
          nodeTable.put(checkInfo.getAddress(), checkInfo);
        }
      }
    }, Constants.HEARTBEAT_INTERVAL + Constants.HEARTBEAT_INTERVAL_DELAY);
  }

  public HashMap<String, NodeInfo> getMap() {
    return new HashMap<String, NodeInfo>(nodeTable);
  }

  public boolean isEmpty() {
    return nodeTable.isEmpty();
  }
}
