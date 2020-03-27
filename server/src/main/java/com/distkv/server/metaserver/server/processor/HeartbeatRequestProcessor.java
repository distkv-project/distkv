package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.entity.Task;
import com.distkv.server.metaserver.server.DmetaServer;
import com.distkv.server.metaserver.server.DmetaStoreClosure;
import com.distkv.server.metaserver.server.bean.HeartbeatRequest;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class HeartbeatRequestProcessor extends AsyncUserProcessor<HeartbeatRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(HeartbeatRequestProcessor.class);

  private final DmetaServer dmetaServer;

  public HeartbeatRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public void handleRequest(final BizContext bizCtx,
                            final AsyncContext asyncCtx,
                            final HeartbeatRequest request) {
    // If it is not currently a leader, return the leader's peer
    if (! this.dmetaServer.getFsm().isLeader()) {
      final HeartbeatResponse response = new HeartbeatResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      asyncCtx.sendResponse(response);
      return;
    }

    final HeartbeatResponse response = new HeartbeatResponse();
    final DmetaStoreClosure closure = new DmetaStoreClosure(dmetaServer, request, response,
        status -> {
          if (!status.isOk()) {
            response.setErrorMessage(status.getErrorMsg());
            response.setSuccess(false);
          }
          asyncCtx.sendResponse(response);
        });

    try {
      final Task task = new Task();
      task.setDone(closure);
      task.setData(ByteBuffer
          .wrap(SerializerManager.getSerializer(SerializerManager.Hessian2).serialize(request)));

      // apply task to raft group.
      dmetaServer.getNode().apply(task);
    } catch (final CodecException e) {
      LOG.error("Fail to encode Request", e);
      response.setSuccess(false);
      response.setErrorMessage(e.getMessage());
      asyncCtx.sendResponse(response);
    }
  }

  @Override
  public String interest() {
    return HeartbeatRequest.class.getName();
  }
}
