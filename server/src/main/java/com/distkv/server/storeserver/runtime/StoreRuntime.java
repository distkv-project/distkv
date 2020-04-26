package com.distkv.server.storeserver.runtime;

import com.distkv.common.NodeInfo;
import com.distkv.common.id.NodeId;
import com.distkv.common.utils.NetUtil;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
import com.distkv.server.storeserver.RunningMode;
import com.distkv.server.storeserver.runtime.expire.ExpirationManager;
import com.distkv.server.storeserver.runtime.heartbeat.HeartbeatManager;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.runtime.workerpool.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;

public class StoreRuntime {

  private static Logger LOG = LoggerFactory.getLogger(StoreRuntime.class);

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

  private volatile ConcurrentHashMap<String, SlaveClient> slaveClients;

  private volatile NodeInfo nodeInfo;

  public StoreRuntime(StoreConfig config) {
    this.config = config;
    storeEngine = new KVStoreImpl();
    expirationManager = new ExpirationManager(config);
    slaveClients = new ConcurrentHashMap<>();
    NodeId nodeId = NodeId.nil();
    nodeInfo = NodeInfo.newBuilder()
        .setAddress(String.format("distkv://%s:%d", NetUtil.getLocalIp(), config.getPort()))
        .setNodeId(nodeId)
        .setIsMaster(false)
        .build();
    if (config.getMode() == RunningMode.DISTRIBUTED) {
      heartbeatManager = new HeartbeatManager(
          nodeInfo,
          config.getMetaServerAddresses(),
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

  public ConcurrentHashMap<String, SlaveClient> getAllSlaveClients() {
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
}
