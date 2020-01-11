package com.distkv.dst.server.runtime;

import com.distkv.dst.server.DstServerConfig;
import com.distkv.dst.server.runtime.workerpool.WorkerPool;

public class DstRuntime {

  private DstServerConfig config;

  private WorkerPool workerPool;

  public DstRuntime(DstServerConfig config) {
    this.config = config;
    workerPool = new WorkerPool(config.getShardNum(), config.isMaster(), config.getSlaves());
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
