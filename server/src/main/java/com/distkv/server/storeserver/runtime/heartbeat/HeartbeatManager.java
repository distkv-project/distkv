package com.distkv.server.storeserver.runtime.heartbeat;

import com.distkv.common.Constants;
import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class HeartbeatManager {
  /**
   * A timer to post heartbeat to MetaServer.
   */
  private static Timer heartbeatTimer = new Timer();

  private static DmetaClient dmetaClient;

  private static Logger LOG = LoggerFactory.getLogger(HeartbeatManager.class);

  public HeartbeatManager(NodeInfo nodeInfo, String dmetaServerListStr,
                          ConcurrentHashMap<String, SlaveClient> clients) {

    dmetaClient = new DmetaClient(dmetaServerListStr);
    heartbeatTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        HeartbeatResponse response = dmetaClient.heartbeat(nodeInfo);
        if (response == null) {
          LOG.warn("Failed to heartbeat to MetaServer, " +
              "and let it retry next ticking.");
          return;
        }
        HashMap<String, NodeInfo> nodeTable = response.getNodeTable();
        if (response.getNodeTable().size() > clients.size() + 1) {
          for (Map.Entry<String, NodeInfo> entry : nodeTable.entrySet()) {
            if (!clients.containsKey(entry.getKey()) &&
                !entry.getKey().equals(nodeInfo.getAddress())) {
              clients.put(entry.getKey(),
                  new SlaveClient(entry.getValue().getAddress()));
            }
          }
        }
        changeNodeInfo(nodeInfo, nodeTable.get(nodeInfo.getAddress()));
      }
    }, 0, Constants.HEARTBEAT_INTERVAL);
  }

  public void changeNodeInfo(NodeInfo old, NodeInfo young) {
    if (old.getNodeId().getIndex() != young.getNodeId().getIndex()) {
      old.getNodeId().setIndex(young.getNodeId().getIndex());
    }
    if (old.isMaster() != young.isMaster()) {
      old.setIsMaster(young.isMaster());
    }
    if (old.getNodeId().getGroupId() == null ||
        !old.getNodeId().getGroupId().equals(young.getNodeId().getGroupId())) {
      old.getNodeId().setGroupId(young.getNodeId().getGroupId());
    }

  }

}
