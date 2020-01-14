package com.distkv.dst.server.runtime;

import com.distkv.dst.server.DstServerConfig;
import com.distkv.dst.server.runtime.salver.SalverClient;
import com.distkv.dst.server.runtime.workerpool.WorkerPool;
import java.util.ArrayList;
import java.util.List;

public class DstRuntime {

  private DstServerConfig config;

  private WorkerPool workerPool;

  private List<SalverClient> salverClients;

  public DstRuntime(DstServerConfig config) {
    this.config = config;

    if (config.isMaster()) {
      salverClients = new ArrayList<>();
      for (String salverAddr : config.getSlaveAddresses()) {
        SalverClient client = new SalverClient(salverAddr);
        salverClients.add(client);
      }
    } else {
      salverClients = null;
    }

    workerPool = new WorkerPool(config.getShardNum(),
        config.isMaster(),
        salverClients);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
