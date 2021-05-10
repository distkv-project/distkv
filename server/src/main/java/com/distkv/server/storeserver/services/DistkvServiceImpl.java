package com.distkv.server.storeserver.services;

import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.entity.Task;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.rpc.service.DistkvService;
import com.distkv.server.storeserver.cluster.ClusterServer;
import com.distkv.server.storeserver.cluster.KVClosure;
import com.google.protobuf.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

public class DistkvServiceImpl implements DistkvService {

  private ClusterServer clusterServer;

  private static final Logger LOG = LoggerFactory.getLogger(DistkvServiceImpl.class);

  public DistkvServiceImpl(ClusterServer clusterServer) {
    this.clusterServer = clusterServer;
  }

  private boolean isLeader() {
    return this.clusterServer.getFsm().isLeader();
  }

  private void handlerNotLeaderError(
      final CompletableFuture<DistkvProtocol.DistkvResponse> future) {
    DistkvProtocol.DistkvResponse.Builder builder =
        DistkvProtocol.DistkvResponse.newBuilder();
    CommonProtocol.SyncIssueResponse.Builder responseBuilder =
        CommonProtocol.SyncIssueResponse.newBuilder();
    responseBuilder.setErrorMsg("Not leader.");
    responseBuilder.setData(getRedirect());
    builder.setResponse(Any.pack(responseBuilder.build()));
    future.complete(builder.setRequestType(DistkvProtocol.RequestType.SYNC_ISSUE).build());
  }

  private String getRedirect() {
    return this.clusterServer.redirect();
  }

  @Override
  public CompletableFuture<DistkvProtocol.DistkvResponse> call(
      DistkvProtocol.DistkvRequest request) {
    CompletableFuture<DistkvProtocol.DistkvResponse> future = new CompletableFuture<>();
    if (!isLeader()) {
      handlerNotLeaderError(future);
      return future;
    }

    try {
      final Task task = new Task();
      task.setData(ByteBuffer.wrap(request.toByteArray()));
      KVClosure closure = new KVClosure() {
        @Override
        public void run(Status status) {

        }
      };
      closure.setFuture(future);
      closure.setRequest(request);
      task.setDone(closure);
      this.clusterServer.getNode().apply(task);
    } catch (Exception e) {
      LOG.error("Something Error{}", e.getMessage());
    }
    return future;
  }

}
