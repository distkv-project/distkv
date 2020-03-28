package com.distkv.server.storeserver.runtime.heartbeat;

import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatManager {
  /**
   * The interval time of heartbeat.
   */
  public static int HEARTBEAT_INTERVAL = 3000;

  /**
   * How long does the heartbeat start after startup.
   */
  public static int INIT_DELAY = 0;

  /**
   * A ThreadPool to mange heartbeat.
   */
  private static ScheduledExecutorService scheduledExecutor = Executors
      .newSingleThreadScheduledExecutor();

  private static DmetaClient dmetaClient;

  private static Logger logger = LoggerFactory.getLogger(HeartbeatManager.class);

  public HeartbeatManager(NodeInfo nodeInfo, String dmetaServerListStr,
                          ConcurrentHashMap<String, SlaveClient> clients) {

    dmetaClient = new DmetaClient(dmetaServerListStr);
    scheduledExecutor.scheduleAtFixedRate(() -> {
      HeartbeatResponse response = dmetaClient.heartbeat(nodeInfo);
      if (response == null) {
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
    }, INIT_DELAY, HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
  }

  public void changeNodeInfo(NodeInfo old, NodeInfo young) {
    if (old.getNodeId().getIndex() != young.getNodeId().getIndex()) {
      old.getNodeId().setIndex(young.getNodeId().getIndex());
    }
    if (old.getNodeId().isMaster() != young.getNodeId().isMaster()) {
      old.getNodeId().setMaster(young.getNodeId().isMaster());
    }
    if (old.getNodeId().getGroupId() == null ||
        !old.getNodeId().getGroupId().equals(young.getNodeId().getGroupId())) {
      old.getNodeId().setGroupId(young.getNodeId().getGroupId());
    }

  }

}
