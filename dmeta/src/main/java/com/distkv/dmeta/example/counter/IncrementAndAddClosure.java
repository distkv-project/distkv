package com.distkv.dmeta.example.counter;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.distkv.dmeta.example.counter.rpc.IncrementAndGetRequest;
import com.distkv.dmeta.example.counter.rpc.ValueResponse;

public class IncrementAndAddClosure implements Closure {
  private CounterServer counterServer;
  private IncrementAndGetRequest request;
  private ValueResponse response;
  private Closure done; // callback

  public IncrementAndAddClosure(CounterServer counterServer,
                                IncrementAndGetRequest request,
                                ValueResponse response,
                                Closure done) {
    super();
    this.counterServer = counterServer;
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

  public IncrementAndGetRequest getRequest() {
    return this.request;
  }

  public void setRequest(IncrementAndGetRequest request) {
    this.request = request;
  }

  public ValueResponse getResponse() {
    return this.response;
  }

}