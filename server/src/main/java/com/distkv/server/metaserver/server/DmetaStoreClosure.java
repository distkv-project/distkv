package com.distkv.server.metaserver.server;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.distkv.server.metaserver.server.bean.HeartbeatRequest;
import com.distkv.server.metaserver.server.bean.HeartbeatResponse;

public class DmetaStoreClosure implements Closure {
  private DmetaServer dmetaServer;
  private HeartbeatRequest request;
  private HeartbeatResponse response;
  private Closure done; // callback

  public DmetaStoreClosure(DmetaServer dmetaServer,
                           HeartbeatRequest request,
                           HeartbeatResponse response,
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

  public HeartbeatRequest getRequest() {
    return this.request;
  }

  public void setRequest(HeartbeatRequest request) {
    this.request = request;
  }

  public HeartbeatResponse getResponse() {
    return this.response;
  }

}
