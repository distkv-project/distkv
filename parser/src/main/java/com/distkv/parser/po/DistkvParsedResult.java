package com.distkv.parser.po;


import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;

public class DistkvParsedResult {
  public RequestType requestType;
  public DistkvRequest request;

  public DistkvParsedResult() {
  }

  public DistkvParsedResult(RequestType requestType, DistkvRequest request) {
    this.requestType = requestType;
    this.request = request;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestType requestType) {
    this.requestType = requestType;
  }

  public DistkvRequest getRequest() {
    return request;
  }

  public void setRequest(DistkvRequest request) {
    this.request = request;
  }
}
