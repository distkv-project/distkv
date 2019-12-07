package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.RequestTypeEnum;
import com.google.common.collect.ImmutableList;

public class WorkerPool {

  private final int shardNum;

  //private HashMap<ShardId, Worker> workers;

  private final ImmutableList<Worker> workers;

  public WorkerPool(int shardNum) {
    this.shardNum = shardNum;
    ImmutableList.Builder<Worker> builder = new ImmutableList.Builder<>();
    for (int i = 0; i < shardNum; ++i) {
      Worker worker = new Worker();
      builder.add(worker);
      worker.start();
    }
    workers = builder.build();
  }

  public void postRequest(
      String key, RequestTypeEnum requestType, Object request, Object completableFuture) {
    final int workerIndex = key.hashCode() % shardNum;
    Worker worker = workers.get(workerIndex);
    try {
      worker.post(new InternalRequest(requestType, request, completableFuture));
    } catch (InterruptedException e) {
      // TODO(qwang): xxxxx
    }
  }

}
