package com.distkv.dst.server.runtime;

import com.distkv.dst.server.DstServerConfig;
import com.distkv.dst.server.runtime.salve.SalveClient;
import com.distkv.dst.server.runtime.workerpool.WorkerPool;
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
        SalveClient client = new SalveClient(salverAddr);
        salveClients.add(client);
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
