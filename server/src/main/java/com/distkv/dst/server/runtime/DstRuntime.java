package com.distkv.dst.server.runtime;

import com.distkv.dst.server.runtime.workerpool.WorkerPool;

public class DstRuntime {

  private static final int shardNum = 8;

  private WorkerPool workerPool;

  public DstRuntime() {
    workerPool = new WorkerPool(shardNum);
  }

  public WorkerPool getWorkerPool() {
    return workerPool;
  }

}
