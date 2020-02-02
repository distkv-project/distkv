package com.distkv.dmeta.example.counter.rpc;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.distkv.dmeta.example.counter.CounterServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetValueRequestProcessor extends SyncUserProcessor<GetValueRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(GetValueRequestProcessor.class);

  private CounterServer counterServer;

  public GetValueRequestProcessor(CounterServer counterServer) {
    super();
    this.counterServer = counterServer;
  }

  @Override
  public Object handleRequest(final BizContext bizCtx,
                              final GetValueRequest request) throws Exception {
    if (!this.counterServer.getFsm().isLeader()) {
      return this.counterServer.redirect();
    }

    final ValueResponse response = new ValueResponse();
    response.setSuccess(true);
    response.setValue(this.counterServer.getFsm().getValue());
    return response;
  }

  @Override
  public String interest() {
    return GetValueRequest.class.getName();
  }
}
