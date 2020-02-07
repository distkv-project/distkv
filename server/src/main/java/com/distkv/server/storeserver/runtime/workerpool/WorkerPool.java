package com.distkv.server.storeserver.runtime.workerpool;

import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class WorkerPool {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerPool.class);

  private final int shardNum;

  private final ImmutableList<Worker> workers;

  private StoreRuntime storeRuntime;

  public WorkerPool(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
    shardNum = storeRuntime.getConfig().getShardNum();
    ImmutableList.Builder<Worker> builder = new ImmutableList.Builder<>();
    for (int i = 0; i < shardNum; ++i) {
      Worker worker = new Worker(storeRuntime);
      builder.add(worker);
      worker.start();
    }
    workers = builder.build();
  }

  @SuppressWarnings({"unchecked"})
  public void postRequest(
      DistkvProtocol.DistkvRequest request, CompletableFuture<DistkvResponse> completableFuture) {
    boolean isMaster = storeRuntime.getConfig().isMaster();
    List<SlaveClient> slaveClients = storeRuntime.getAllSlaveClients();

    if (isMaster) {
      for (SlaveClient client : slaveClients) {
        synchronized (client) {
          try {
            DistkvProtocol.DistkvResponse response =
                client.getDistkvService().call(request).get();
            if (response.getStatus() != CommonProtocol.Status.OK) {
              completableFuture.complete(DistkvProtocol.DistkvResponse.newBuilder()
                  .setStatus(CommonProtocol.Status.SYNC_ERROR).build());
            }
          } catch (ExecutionException | InterruptedException e) {
            completableFuture.complete(DistkvProtocol.DistkvResponse.newBuilder()
                .setStatus(CommonProtocol.Status.SYNC_ERROR).build());
            LOGGER.error("Process terminated because write to salve failed");
            Runtime.getRuntime().exit(-1);
          }
        }
      }
    }

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
