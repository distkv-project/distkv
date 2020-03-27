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

  private static ScheduledExecutorService scheduledExecutor = Executors
      .newSingleThreadScheduledExecutor();

  private static DmetaClient dmetaClient;

  private static HashSet<String> buildSet;

  private static AtomicInteger i = new AtomicInteger(0);

  public HeartbeatManager(NodeInfo nodeInfo, int heartBeatInterval,
                          String dmetaServerListStr, CopyOnWriteArrayList<SlaveClient> clients) {
    buildSet = new HashSet<>();
    buildSet.add(nodeInfo.getAddress());
    dmetaClient = new DmetaClient(dmetaServerListStr);
    scheduledExecutor.scheduleAtFixedRate(() -> {
      HeartbeatResponse response = dmetaClient.heartbeat(nodeInfo);
      if (response.getNodeTable().size() > buildSet.size()) {
        HashMap<String, NodeInfo> map = response.getNodeTable();
        for (Map.Entry<String, NodeInfo> entry : map.entrySet()) {
          if (!buildSet.contains(entry.getKey())) {
            clients.add(new SlaveClient(entry.getValue().getAddress()));
            buildSet.add(entry.getKey());
          }
        }
      }
      nodeInfo.getNodeId().setMaster(response.getNodeTable()
          .get(nodeInfo.getAddress()).getNodeId().isMaster());
    }, 100, heartBeatInterval, TimeUnit.MILLISECONDS);
  }

}
