package com.distkv.server.storeserver.runtime.heartbeat;

import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.client.DmetaClient;
import com.distkv.server.metaserver.server.bean.HeartBeatResponse;
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
    buildSet.add(nodeInfo.getNodeName());
    dmetaClient = new DmetaClient(dmetaServerListStr);
    scheduledExecutor.scheduleAtFixedRate(() -> {
      HeartBeatResponse response = dmetaClient.heartBeat(nodeInfo);
      if (response.getNodeTable().size() != clients.size()-1) {
        for (NodeInfo node : response.getNodeTable().values()) {
          if (!buildSet.contains(nodeInfo.getNodeName())) {
            clients.add(new SlaveClient(node.getIpAndPort()));
          }
        }
      }
      System.out.println(response.getNodeTable()
          .get(nodeInfo.getNodeName()).isMaster() +
          nodeInfo.getIpAndPort() +
          "clientsS=" + clients.size());
      nodeInfo.setMaster(response.getNodeTable()
          .get(nodeInfo.getNodeName()).isMaster());
    }, 100, heartBeatInterval, TimeUnit.MILLISECONDS);
  }

}
