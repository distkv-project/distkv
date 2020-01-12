package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.RequestTypeEnum;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WorkerPool {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerPool.class);

  private final int shardNum;

  private boolean isMaster;

  private List<String> slaveAddresses;

  private final ImmutableList<Worker> workers;

  public WorkerPool(int shardNum, boolean isMaster, List<String> slaveAddresses) {
    this.shardNum = shardNum;
    this.isMaster = isMaster;
    this.slaveAddresses = slaveAddresses;
    ImmutableList.Builder<Worker> builder = new ImmutableList.Builder<>();
    for (int i = 0; i < shardNum; ++i) {
      Worker worker = new Worker(isMaster, slaveAddresses);
      builder.add(worker);
      worker.start();
    }
    workers = builder.build();
  }

  public void postRequest(
      String key, RequestTypeEnum requestType, Object request, Object completableFuture) {
    final int workerIndex = Math.abs(key.hashCode()) % shardNum;
    Worker worker = workers.get(workerIndex);
    try {
      worker.post(new InternalRequest(requestType, request, completableFuture));
    } catch (InterruptedException e) {
      // TODO(qwang): Should be an assert here.
      LOGGER.error("Failed to post request to worker pool, key is {}", key);
    }
  }

}
