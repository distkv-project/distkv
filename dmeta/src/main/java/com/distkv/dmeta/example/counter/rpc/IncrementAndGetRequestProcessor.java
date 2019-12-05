package com.distkv.dmeta.example.counter.rpc;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.entity.Task;
import com.distkv.dmeta.example.counter.CounterServer;
import com.distkv.dmeta.example.counter.IncrementAndAddClosure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class IncrementAndGetRequestProcessor extends AsyncUserProcessor<IncrementAndGetRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(IncrementAndGetRequestProcessor.class);

  private final CounterServer counterServer;

  public IncrementAndGetRequestProcessor(CounterServer counterServer) {
    super();
    this.counterServer = counterServer;
  }

  @Override
  public void handleRequest(final BizContext bizCtx,
                            final AsyncContext asyncCtx,
                            final IncrementAndGetRequest request) {
    if (!this.counterServer.getFsm().isLeader()) {
      asyncCtx.sendResponse(this.counterServer.redirect());
      return;
    }

    final ValueResponse response = new ValueResponse();
    final IncrementAndAddClosure closure =
        new IncrementAndAddClosure(counterServer, request, response,
            status -> {
              if (!status.isOk()) {
                response.setErrorMsg(status.getErrorMsg());
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
      counterServer.getNode().apply(task);
    } catch (final CodecException e) {
      LOG.error("Fail to encode IncrementAndGetRequest", e);
      response.setSuccess(false);
      response.setErrorMsg(e.getMessage());
      asyncCtx.sendResponse(response);
    }
  }

  @Override
  public String interest() {
    return IncrementAndGetRequest.class.getName();
  }
}