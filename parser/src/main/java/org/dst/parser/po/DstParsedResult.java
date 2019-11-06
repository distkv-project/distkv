package org.dst.parser.po;

public class DstParsedResult {
  public String requestType;
  public Object request;

  public DstParsedResult() {
  }

  public DstParsedResult(String requestType, Object request) {
    this.requestType = requestType;
    this.request = request;
  }

  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

  public Object getRequest() {
    return request;
  }

  public void setRequest(Object request) {
    this.request = request;
  }
}
