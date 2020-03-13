package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.distkv.server.metaserver.server.DmetaServer;
import com.distkv.server.metaserver.server.bean.GetRequest;
import com.distkv.server.metaserver.server.bean.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetValueRequestProcessor extends SyncUserProcessor<GetRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(GetValueRequestProcessor.class);

  private DmetaServer dmetaServer;

  public GetValueRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public Object handleRequest(final BizContext bizCtx,
                              final GetRequest request) throws Exception {
    if (!this.dmetaServer.getFsm().isLeader()) {
      final GetResponse response = new GetResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      return response;
    }

    final GetResponse response = new GetResponse();
    try {
      response.setValue(this.dmetaServer.getFsm().getGlobalView().get(request.getKey()));
    } catch (Exception e) {
      response.setSuccess(false);
      return response;
    }
    response.setSuccess(true);
    return response;
  }

  @Override
  public String interest() {
    return GetRequest.class.getName();
  }
}
