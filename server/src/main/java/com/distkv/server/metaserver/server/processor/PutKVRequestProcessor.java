package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.entity.Task;
import com.distkv.server.metaserver.server.DmetaServer;
import com.distkv.server.metaserver.server.DmetaStoreClosure;
import com.distkv.server.metaserver.server.bean.PutRequest;
import com.distkv.server.metaserver.server.bean.PutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class PutKVRequestProcessor extends AsyncUserProcessor<PutRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(PutKVRequestProcessor.class);

  private final DmetaServer dmetaServer;

  public PutKVRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public void handleRequest(final BizContext bizCtx,
                            final AsyncContext asyncCtx,
                            final PutRequest request) {
    if (! this.dmetaServer.getFsm().isLeader()) {
      final PutResponse response = new PutResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      asyncCtx.sendResponse(response);
      return;
    }

    final PutResponse response = new PutResponse();
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
      LOG.error("Fail to encode IncrementAndGetRequest", e);
      response.setSuccess(false);
      response.setErrorMessage(e.getMessage());
      asyncCtx.sendResponse(response);
    }
  }

  @Override
  public String interest() {
    return PutRequest.class.getName();
  }
}
