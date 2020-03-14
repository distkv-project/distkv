package com.distkv.server.metaserver.server;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.distkv.server.metaserver.server.bean.PutRequest;
import com.distkv.server.metaserver.server.bean.PutResponse;

public class DmetaStoreClosure implements Closure {
  private DmetaServer dmetaServer;
  private PutRequest request;
  private PutResponse response;
  private Closure done; // callback

  public DmetaStoreClosure(DmetaServer dmetaServer,
                           PutRequest request,
                           PutResponse response,
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

  public PutRequest getRequest() {
    return this.request;
  }

  public void setRequest(PutRequest request) {
    this.request = request;
  }

  public PutResponse getResponse() {
    return this.response;
  }

}
