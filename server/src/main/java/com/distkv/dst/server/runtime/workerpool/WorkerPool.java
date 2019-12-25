package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.server.DstServerConfig;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerPool {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerPool.class);

  private DstServerConfig config;

  private final ImmutableList<Worker> workers;

  public WorkerPool(DstServerConfig config) {
    this.config = config;
    ImmutableList.Builder<Worker> builder = new ImmutableList.Builder<>();
    for (int i = 0; i < config.getShardNum(); ++i) {
      Worker worker = new Worker();
      builder.add(worker);
      worker.start();
    }
    workers = builder.build();
  }

  public void postRequest(
      String key, RequestTypeEnum requestType, Object request, Object completableFuture) {
    final int workerIndex = Math.abs(key.hashCode()) % config.getShardNum();
    Worker worker = workers.get(workerIndex);
    try {
      worker.post(new InternalRequest(requestType, request, completableFuture));
    } catch (InterruptedException e) {
      // TODO(qwang): Should be an assert here.
      LOGGER.error("Failed to post request to worker pool, key is {}", key);
    }
  }

}
