package com.distkv.server.storeserver.runtime.heartbeat;

import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HeartbeatManager {

  public static int HEARTBEAT_INTERVAL = 500;

  public static int HEARTBEAT_TIMEOUT = 3000;

  public static int INIT_DELAY = 0;

  private static ScheduledExecutorService scheduledExecutor = Executors
      .newSingleThreadScheduledExecutor();

  private static DmetaClient dmetaClient;

  private static HashSet<String> buildSet;

  private static Logger LOGGER = LoggerFactory.getLogger(HeartbeatManager.class);

  public HeartbeatManager(NodeInfo nodeInfo, String dmetaServerListStr,
                          CopyOnWriteArrayList<SlaveClient> clients) {
    buildSet = new HashSet<>();
    buildSet.add(nodeInfo.getAddress());
    try {
      dmetaClient = new DmetaClient(dmetaServerListStr);
    } catch (InterruptedException | TimeoutException e) {
      LOGGER.error("Fail to init dmeta client");
      System.exit(-1);
    }

    scheduledExecutor.scheduleAtFixedRate(() -> {
      HeartbeatResponse response = dmetaClient.heartbeat(nodeInfo, HEARTBEAT_TIMEOUT);
      HashMap<String, NodeInfo> nodeTable = response.getNodeTable();
      if (response.getNodeTable().size() > buildSet.size()) {
        for (Map.Entry<String, NodeInfo> entry : nodeTable.entrySet()) {
          if (!buildSet.contains(entry.getKey())) {
            clients.add(new SlaveClient(entry.getValue().getAddress()));
            buildSet.add(entry.getKey());
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

  }

}
