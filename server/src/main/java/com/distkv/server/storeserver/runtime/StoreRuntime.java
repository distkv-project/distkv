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

  /**
   * A manager that reports the heartbeats to MetaServer.
   */
  private HeartbeatManager heartbeatManager;

  /**
   * The worker pool that executes requests by sharding.
   */
  private WorkerPool workerPool;

  /**
   * The clients to connect to all slaves.
   */
  private volatile ConcurrentHashMap<String, SlaveClient> slaveClients;

  /**
   * The information of this node.
   */
  private volatile NodeInfo nodeInfo;

  public StoreRuntime(StoreConfig config) {
    this.config = config;
    // Initial store engine.
    storeEngine = new KVStoreImpl();
    // Initial expiration manager.
    expirationManager = new ExpirationManager(config);
    // Initial clients connecting to MetaServer.
    slaveClients = new ConcurrentHashMap<>();
    nodeInfo = NodeInfo.newBuilder()
        .setAddress(String.format("distkv://%s:%d", NetUtil.getLocalIp(), config.getPort()))
        .setNodeId(NodeId.nil())
        .setIsMaster(false)
        .build();
    if (config.getMode() == RunningMode.DISTRIBUTED) {
      // Initial the heartbeat manager if this node is running under DISTRIBUTED mode.
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
