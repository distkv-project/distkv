package com.distkv.server.runtime;

import com.distkv.common.utils.RuntimeUtil;
import com.distkv.server.runtime.salve.SalveClient;
import com.distkv.server.DistKVServerConfig;
import com.distkv.server.runtime.workerpool.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class DistKVRuntime {

  private static Logger LOGGER = LoggerFactory.getLogger(DistKVRuntime.class);

  private DistKVServerConfig config;

  private WorkerPool workerPool;

  private List<SalveClient> salveClients;

  public DistKVRuntime(DistKVServerConfig config) {
    this.config = config;

    if (config.isMaster()) {
      salveClients = new ArrayList<>();
      for (String salverAddr : config.getSlaveAddresses()) {
        final SalveClient[] client = {null};
        RuntimeUtil.waitForCondition(() -> {
          try {
            client[0] = new SalveClient(salverAddr);
            return true;
            //TODO : Drpc need to add a Exception to cover
            // io.netty.channel.AbstractChannel$AnnotatedConnectException
          } catch (Exception e) {
            return false;
          }
        }, 5 * 60 * 1000);
        salveClients.add(client[0]);
        LOGGER.info("Connecting to salver(" + salverAddr + ") success");
      }
    } else {
      salveClients = null;
    }

    workerPool = new WorkerPool(this);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

  public DistKVServerConfig getConfig() {
    return config;
  }

  public List<SalveClient> getAllSalveClients() {
    return salveClients;
  }

  public void shutdown() {
    workerPool.shutdown();
  }

}
