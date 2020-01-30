package com.distkv.server.storeserver.runtime;

import com.distkv.common.utils.RuntimeUtil;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import com.distkv.server.storeserver.StoreServerConfig;
import com.distkv.server.storeserver.runtime.workerpool.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class StoreRuntime {

  private static Logger LOGGER = LoggerFactory.getLogger(StoreRuntime.class);

  private StoreServerConfig config;

  private WorkerPool workerPool;

  private List<SlaveClient> slaveClients;

  public StoreRuntime(StoreServerConfig config) {
    this.config = config;

    if (config.isMaster()) {
      slaveClients = new ArrayList<>();
      for (String slaveAddr : config.getSlaveAddresses()) {
        final SlaveClient[] client = {null};
        RuntimeUtil.waitForCondition(() -> {
          try {
            client[0] = new SlaveClient(slaveAddr);
            return true;
            //TODO : Drpc need to add a Exception to cover
            // io.netty.channel.AbstractChannel$AnnotatedConnectException
          } catch (Exception e) {
            return false;
          }
        }, 5 * 60 * 1000);
        slaveClients.add(client[0]);
        LOGGER.info("Connecting to slave(" + slaveAddr + ") success");
      }
    } else {
      slaveClients = null;
    }

    workerPool = new WorkerPool(this);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

  public StoreServerConfig getConfig() {
    return config;
  }

  public List<SlaveClient> getAllSlaveClients() {
    return slaveClients;
  }

  public void shutdown() {
    workerPool.shutdown();
  }

}
