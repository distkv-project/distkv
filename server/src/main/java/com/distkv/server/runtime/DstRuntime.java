package com.distkv.server.runtime;

import com.distkv.common.utils.RuntimeUtil;
import com.distkv.server.DstServerConfig;
import com.distkv.server.runtime.salve.SalveClient;
import com.distkv.server.runtime.workerpool.WorkerPool;
import java.util.ArrayList;
import java.util.List;

public class DstRuntime {

  private DstServerConfig config;

  private WorkerPool workerPool;

  private List<SalveClient> salveClients;

  public DstRuntime(DstServerConfig config) {
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
          } catch (RuntimeException e) {
            return false;
          }
        }, 5 * 60 * 1000);
        salveClients.add(client[0]);
      }
    } else {
      salveClients = null;
    }

    workerPool = new WorkerPool(config.getShardNum(),
        config.isMaster(),
        salveClients);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
