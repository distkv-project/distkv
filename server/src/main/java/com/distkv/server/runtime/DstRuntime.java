package com.distkv.server.runtime;

import com.distkv.server.DstServerConfig;
import com.distkv.server.runtime.workerpool.WorkerPool;

public class DstRuntime {

  private DstServerConfig config;

  private WorkerPool workerPool;

  public DstRuntime(DstServerConfig config) {
    this.config = config;
    workerPool = new WorkerPool(config.getShardNum(), config.isMaster());
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
