package com.distkv.server.metaserver.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.distkv.server.metaserver.server.bean.HeartbeatRequest;

public class GetNodeTableProcessor extends AsyncUserProcessor<GetNodeTableProcessor> {

  @Override
  public void handleRequest(BizContext bizContext, AsyncContext asyncContext, GetNodeTableProcessor getNodeTableProcessor) {

  }

  @Override
  public String interest() {
    return GetNodeTableProcessor.class.getName();
  }
}
