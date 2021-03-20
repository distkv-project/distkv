package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.entity.Task;
import com.alipay.sofa.jraft.rpc.RpcContext;
import com.alipay.sofa.jraft.rpc.RpcProcessor;
import com.distkv.server.metaserver.server.DmetaServer;
import com.distkv.server.metaserver.server.DmetaStoreClosure;
import com.distkv.server.metaserver.server.bean.HeartbeatRequest;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class HeartbeatRequestProcessor implements RpcProcessor<HeartbeatRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(HeartbeatRequestProcessor.class);

  private final DmetaServer dmetaServer;

  public HeartbeatRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public void handleRequest(RpcContext rpcCtx, HeartbeatRequest request) {
    // If it is not currently a leader, return the leader's peer.
    if (!this.dmetaServer.getFsm().isLeader()) {
      final HeartbeatResponse response = new HeartbeatResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      rpcCtx.sendResponse(response);
      return;
    }

    final HeartbeatResponse response = new HeartbeatResponse();
    final DmetaStoreClosure closure = new DmetaStoreClosure(dmetaServer, request, response,
        status -> {
          if (!status.isOk()) {
            response.setErrorMessage(status.getErrorMsg());
            response.setSuccess(false);
          }
          rpcCtx.sendResponse(response);
        });

    try {
      final Task task = new Task();
      task.setData(ByteBuffer
          .wrap(SerializerManager.getSerializer(SerializerManager.Hessian2).serialize(request)));
      task.setDone(closure);
      // apply task to raft group.
      dmetaServer.getNode().apply(task);
    } catch (final CodecException e) {
      LOG.error("Fail to encode Request", e);
      response.setSuccess(false);
      response.setErrorMessage(e.getMessage());
      rpcCtx.sendResponse(response);
    }
  }


  @Override
  public String interest() {
    return HeartbeatRequest.class.getName();
  }
}
