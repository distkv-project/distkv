package com.distkv.server.storeserver.runtime;

import com.distkv.common.NodeInfo;
import com.distkv.common.id.GroupId;
import com.distkv.common.id.NodeId;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import com.distkv.server.storeserver.runtime.expire.ExpirationManager;
import com.distkv.server.storeserver.runtime.heartbeat.HeartbeatManager;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.runtime.workerpool.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StoreRuntime {

  private static Logger LOGGER = LoggerFactory.getLogger(StoreRuntime.class);

  private StoreConfig config;

  /**
   * Store engine.
   */
  private KVStore storeEngine;

  /**
   * A manager that handles key expire requests.
   */
  private ExpirationManager expirationManager;

  private HeartbeatManager heartbeatManager;

  private WorkerPool workerPool;

  private volatile CopyOnWriteArrayList<SlaveClient> slaveClients;

  private volatile NodeInfo nodeInfo;

  private short groupIndex = 1;

  public StoreRuntime(StoreConfig config) {
    this.config = config;
    storeEngine = new KVStoreImpl();
    expirationManager = new ExpirationManager(config);
    slaveClients = new CopyOnWriteArrayList<>();
    GroupId groupId = GroupId.fromShort(groupIndex);
    NodeId nodeId = NodeId.from(-1, groupId, false);
    nodeInfo = NodeInfo.newBuilder()
        .setAddress(String.format("distkv://%s:%d", getLocalIp(), config.getPort()))
        .setNodeId(nodeId)
        .build();
    if (config.getMode().equals("distributed")) {
      heartbeatManager = new HeartbeatManager(
          nodeInfo,
          config.getDmetaServerListStr(),
          slaveClients);
    }

    workerPool = new WorkerPool(this);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

  public StoreConfig getConfig() {
    return config;
  }

  public KVStore getStoreEngine() {
    return storeEngine;
  }

  public ExpirationManager getExpirationManager() {
    return expirationManager;
  }

  public List<SlaveClient> getAllSlaveClients() {
    return slaveClients;
  }

  public void shutdown() {
    workerPool.shutdown();
  }

  public NodeInfo getNodeInfo() {
    return nodeInfo;
  }

  public synchronized void setNodeInfo(NodeInfo nodeInfo) {
    this.nodeInfo = nodeInfo;
  }

  public static String getLocalIp() {
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      String ip = inetAddress.getHostAddress().toString();
      return ip;
    } catch (UnknownHostException e) {
      return "localhost";
    }
  }
}
