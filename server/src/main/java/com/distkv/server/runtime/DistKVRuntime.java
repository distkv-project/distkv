package com.distkv.server.runtime;

import com.distkv.common.utils.RuntimeUtil;
import com.distkv.server.runtime.slave.SlaveClient;
import com.distkv.server.storeserver.DistKVServerConfig;
import com.distkv.server.runtime.workerpool.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class DistKVRuntime {

  private static Logger LOGGER = LoggerFactory.getLogger(DistKVRuntime.class);

  private DistKVServerConfig config;

  private WorkerPool workerPool;

  private List<SlaveClient> slaveClients;

  public DistKVRuntime(DistKVServerConfig config) {
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

  public DistKVServerConfig getConfig() {
    return config;
  }

  public List<SlaveClient> getAllSlaveClients() {
    return slaveClients;
  }

  public void shutdown() {
    workerPool.shutdown();
  }

}
