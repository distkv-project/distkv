package org.dst.parse.po;

public class DstRequestReslut {
  public String requestType;
  public Object request;

  public DstRequestReslut() {
  }

  public DstRequestReslut(String requestType, Object request) {
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
