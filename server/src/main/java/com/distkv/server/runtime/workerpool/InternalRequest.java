package com.distkv.server.runtime.workerpool;

import com.distkv.common.RequestTypeEnum;

public class InternalRequest {

  private RequestTypeEnum requestType;

  private Object request;

  private Object completableFuture;

  public InternalRequest(RequestTypeEnum requestType, Object request, Object completableFuture) {
    this.requestType = requestType;
    this.request = request;
    this.completableFuture = completableFuture;
  }

  public Object getRequest() {
    return request;
  }

  public Object getCompletableFuture() {
    return completableFuture;
  }

  public RequestTypeEnum getRequestType() {
    return requestType;
  }

}
