package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.distkv.server.metaserver.server.DmetaServer;
import com.distkv.server.metaserver.server.bean.GetGlobalViewRequest;
import com.distkv.server.metaserver.server.bean.GetGlobalViewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetGlobalViewRequestProcessor extends AsyncUserProcessor<GetGlobalViewRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(HeartbeatRequestProcessor.class);

  private final DmetaServer dmetaServer;

  public GetGlobalViewRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public void handleRequest(BizContext bizContext,
                            AsyncContext asyncContext,
                            GetGlobalViewRequest getGlobalViewRequest) {
    // If it is not currently a leader, return the leader's peer.
    if (!this.dmetaServer.getFsm().isLeader()) {
      final GetGlobalViewResponse response = new GetGlobalViewResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      asyncContext.sendResponse(response);
      return;
    }

    final GetGlobalViewResponse response = new GetGlobalViewResponse();
    response.setSuccess(true);
    response.setGlobalView(dmetaServer.getFsm()
        .getGlobalView().getGlobalViewTable());
    asyncContext.sendResponse(response);
  }

  @Override
  public String interest() {
    return GetGlobalViewRequest.class.getName();
  }
}
