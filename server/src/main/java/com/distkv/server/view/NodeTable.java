package com.distkv.server.view;

import com.distkv.common.Constants;
import com.distkv.common.NodeInfo;
import com.distkv.common.NodeState;
import org.apache.commons.lang.math.RandomUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
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

  private volatile boolean hasMaster;

  public NodeTable() {
    nodeTable = new ConcurrentHashMap<>();
    hasMaster = false;
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

  public void selectMaster() {
    if (nodeTable.size() == 0) {
      return;
    }

    if (hasMaster != false) {
      //1. find now master.
      NodeInfo oldMaster = null;
      for (NodeInfo nodeInfo : nodeTable.values()) {
        if (nodeInfo.isMaster()) {
          oldMaster = nodeInfo;
        }
      }
      synchronized (oldMaster) {
        //2. judge master state.
        if (oldMaster.getState() == NodeState.DEAD) {
          //3. select a master node.
          nodeTable.remove(oldMaster.getAddress());
          getActiveNode().setIsMaster(true);
        }
      }
    } else {
      getActiveNode().setIsMaster(true);
    }
  }

  public NodeInfo getActiveNode() {
    int randomKey = RandomUtils.nextInt(nodeTable.size());
    NodeInfo nodeInfo = (NodeInfo) nodeTable.values().toArray()[randomKey];
    return nodeInfo;
  }
}
