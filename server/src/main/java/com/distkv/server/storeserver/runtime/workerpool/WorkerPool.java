package com.distkv.server.storeserver.runtime.workerpool;

import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.google.common.collect.ImmutableList;
import java.util.concurrent.CompletableFuture;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class WorkerPool {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerPool.class);

  private final int shardNum;

  private final ImmutableList<Worker> workers;


  public WorkerPool(StoreRuntime storeRuntime) {
    shardNum = storeRuntime.getConfig().getShardNum();
    ImmutableList.Builder<Worker> builder = new ImmutableList.Builder<>();
    for (int i = 0; i < shardNum; ++i) {
      Worker worker = new Worker(storeRuntime);
      builder.add(worker);
      worker.start();
    }
    workers = builder.build();
  }

  public void postRequest(
      DistkvProtocol.DistkvRequest request, CompletableFuture<DistkvResponse> completableFuture) {
    String key = request.getKey();
    final int workerIndex = Math.abs(key.hashCode()) % shardNum;
    Worker worker = workers.get(workerIndex);
    try {
      worker.post(new InternalRequest(request, completableFuture));
    } catch (InterruptedException e) {
      // TODO(qwang): Should be an assert here.
      LOGGER.error("Failed to post request to worker pool, key is {}", key);

    }
  }

  public void shutdown() {
    // TODO(qwang): finish this.
  }

}
