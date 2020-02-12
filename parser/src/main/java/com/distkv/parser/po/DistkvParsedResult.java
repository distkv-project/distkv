package com.distkv.parser.po;

import com.distkv.common.RequestTypeEnum;

public class DistkvParsedResult {
  public RequestTypeEnum requestType;
  public Object request;

  public DistkvParsedResult() {
  }

  public DistkvParsedResult(RequestTypeEnum requestType, Object request) {
    this.requestType = requestType;
    this.request = request;
  }

  public RequestTypeEnum getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestTypeEnum requestType) {
    this.requestType = requestType;
  }

  public Object getRequest() {
    return request;
  }

  public void setRequest(Object request) {
    this.request = request;
  }
}
