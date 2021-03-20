package com.distkv.server.metaserver.server.processor;

import com.alipay.sofa.jraft.rpc.RpcContext;
import com.alipay.sofa.jraft.rpc.RpcProcessor;
import com.distkv.server.metaserver.server.DmetaServer;
import com.distkv.server.metaserver.server.bean.GetGlobalViewRequest;
import com.distkv.server.metaserver.server.bean.GetGlobalViewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetGlobalViewRequestProcessor implements RpcProcessor<GetGlobalViewRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(HeartbeatRequestProcessor.class);

  private final DmetaServer dmetaServer;

  public GetGlobalViewRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public void handleRequest(RpcContext rpcCtx, GetGlobalViewRequest request) {
    // If it is not a leader, return the leader's peer.
    if (!this.dmetaServer.getFsm().isLeader()) {
      final GetGlobalViewResponse response = new GetGlobalViewResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      rpcCtx.sendResponse(response);
      return;
    }

    final GetGlobalViewResponse response = new GetGlobalViewResponse();
    response.setSuccess(true);
    response.setGlobalView(dmetaServer.getFsm()
        .getGlobalView().getGlobalViewTable());
    rpcCtx.sendResponse(response);
  }

  @Override
  public String interest() {
    return GetGlobalViewRequest.class.getName();
  }
}
