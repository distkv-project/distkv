package com.distkv.dmeta.server;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.distkv.dmeta.server.bean.PutKVRequest;
import com.distkv.dmeta.server.bean.PutKVResponse;

public class DmetaStoreClosure implements Closure {
  private DmetaServer dmetaServer;
  private PutKVRequest request;
  private PutKVResponse response;
  private Closure done; // callback

  public DmetaStoreClosure(DmetaServer dmetaServer,
                           PutKVRequest request,
                           PutKVResponse response,
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

  public PutKVRequest getRequest() {
    return this.request;
  }

  public void setRequest(PutKVRequest request) {
    this.request = request;
  }

  public PutKVResponse getResponse() {
    return this.response;
  }

}
