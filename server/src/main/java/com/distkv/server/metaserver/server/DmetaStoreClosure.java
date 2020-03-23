package com.distkv.server.metaserver.server;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.distkv.server.metaserver.server.bean.HeartBeatRequest;
import com.distkv.server.metaserver.server.bean.HeartBeatResponse;

public class DmetaStoreClosure implements Closure {
  private DmetaServer dmetaServer;
  private HeartBeatRequest request;
  private HeartBeatResponse response;
  private Closure done; // callback

  public DmetaStoreClosure(DmetaServer dmetaServer,
                           HeartBeatRequest request,
                           HeartBeatResponse response,
                           Closure done) {
    super();
    this.dmetaServer = dmetaServer;
    this.request = request;
    this.response = response;
    this.done = done;
  }

  @Override
  public void run(Status status) {
    // callback to client
    if (this.done != null) {
      done.run(status);
    }
  }

  public HeartBeatRequest getRequest() {
    return this.request;
  }

  public void setRequest(HeartBeatRequest request) {
    this.request = request;
  }

  public HeartBeatResponse getResponse() {
    return this.response;
  }

}
