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

  private static ScheduledExecutorService scheduledExecutor = Executors
      .newSingleThreadScheduledExecutor();

  private static DmetaClient dmetaClient;

  private static HashSet<String> buildSet;

  private static AtomicInteger i = new AtomicInteger(0);

  public HeartbeatManager(NodeInfo nodeInfo, String dmetaServerListStr,
                          CopyOnWriteArrayList<SlaveClient> clients) {
    buildSet = new HashSet<>();
    buildSet.add(nodeInfo.getAddress());
    dmetaClient = new DmetaClient(dmetaServerListStr);
    scheduledExecutor.scheduleAtFixedRate(() -> {
      HeartbeatResponse response = dmetaClient.heartbeat(nodeInfo);
      HashMap<String, NodeInfo> nodeTable = response.getNodeTable();
      if (response.getNodeTable().size() > buildSet.size()) {
        for (Map.Entry<String, NodeInfo> entry : nodeTable.entrySet()) {
          if (!buildSet.contains(entry.getKey())) {
            clients.add(new SlaveClient(entry.getValue().getAddress()));
            buildSet.add(entry.getKey());
          }
        }
      }

      System.out.println(nodeTable.get(nodeInfo.getAddress()).getNodeId().isMaster()
          + nodeTable.get(nodeInfo.getAddress()).getAddress()
          + clients.size()
      );
      nodeInfo.getNodeId().setMaster(response.getNodeTable()
          .get(nodeInfo.getAddress()).getNodeId().isMaster());
    }, 0, HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
  }

}
