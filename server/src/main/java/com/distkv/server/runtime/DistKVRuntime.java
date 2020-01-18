package com.distkv.server.runtime;

import com.distkv.server.DistKVServerConfig;
import com.distkv.server.runtime.workerpool.WorkerPool;

public class DistKVRuntime {

  private DistKVServerConfig config;

  private WorkerPool workerPool;

  public DistKVRuntime(DistKVServerConfig config) {
    this.config = config;
    workerPool = new WorkerPool(config.getShardNum(), config.isMaster());
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
