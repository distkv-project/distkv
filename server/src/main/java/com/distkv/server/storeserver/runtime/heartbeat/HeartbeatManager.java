package com.distkv.server.storeserver.runtime.heartbeat;

import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartbeatManager {

  public static int HEARTBEAT_INTERVAL = 500;

  public static int HEARTBEAT_TIMEOUT = 3000;

  public static int INIT_DELAY = 0;

  private static ScheduledExecutorService scheduledExecutor = Executors
      .newSingleThreadScheduledExecutor();

  private static DmetaClient dmetaClient;

  private static HashSet<String> buildSet;

  public HeartbeatManager(NodeInfo nodeInfo, String dmetaServerListStr,
                          CopyOnWriteArrayList<SlaveClient> clients) {
    buildSet = new HashSet<>();
    buildSet.add(nodeInfo.getAddress());
    dmetaClient = new DmetaClient(dmetaServerListStr);
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
      nodeInfo.getNodeId().setMaster(response.getNodeTable()
          .get(nodeInfo.getAddress()).getNodeId().isMaster());
    }, INIT_DELAY, HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
  }

}
