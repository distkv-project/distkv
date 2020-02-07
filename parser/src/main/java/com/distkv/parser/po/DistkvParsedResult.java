package com.distkv.parser.po;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;

public class DistkvParsedResult {
  public RequestType requestType;
  public Object request;

  public DistkvParsedResult() {
  }

  public DistkvParsedResult(RequestType requestType, Object request) {
    this.requestType = requestType;
    this.request = request;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestType requestType) {
    this.requestType = requestType;
  }

  public Object getRequest() {
    return request;
  }

  public void setRequest(Object request) {
    this.request = request;
  }
}
