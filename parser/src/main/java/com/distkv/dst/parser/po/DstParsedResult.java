package com.distkv.dst.parser.po;

import com.distkv.dst.common.RequestTypeEnum;

public class DstParsedResult {
  public RequestTypeEnum requestType;
  public Object request;

  public DstParsedResult() {
  }

  public DstParsedResult(RequestTypeEnum requestType, Object request) {
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
