package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.distkv.dmeta.server.DmetaServer;
import com.distkv.dmeta.server.bean.GetValueRequest;
import com.distkv.dmeta.server.bean.GetValueResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetValueRequestProcessor extends SyncUserProcessor<GetValueRequest> {

  private static final Logger LOG = LoggerFactory.getLogger(GetValueRequestProcessor.class);

  private DmetaServer dmetaServer;

  public GetValueRequestProcessor(DmetaServer dmetaServer) {
    super();
    this.dmetaServer = dmetaServer;
  }

  @Override
  public Object handleRequest(final BizContext bizCtx,
                              final GetValueRequest request) throws Exception {
    if (!this.dmetaServer.getFsm().isLeader()) {
      final GetValueResponse response = new GetValueResponse();
      response.setSuccess(false);
      response.setRedirect(dmetaServer.getRedirect());
      return response;
    }

    final GetValueResponse response = new GetValueResponse();
    try {
      response.setValue(this.dmetaServer.getFsm().getValueByPath(request.getPath()));
    } catch (Exception e) {
      response.setSuccess(false);
      return response;
    }
    response.setSuccess(true);
    return response;
  }

  @Override
  public String interest() {
    return GetValueRequest.class.getName();
  }
}
