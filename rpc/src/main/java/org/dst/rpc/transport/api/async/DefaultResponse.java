package org.dst.rpc.transport.api.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zrj CreateDate: 2019/10/29
 */
public class DefaultResponse implements Response {

  private long requestId;

  private Object value;

  private Exception exception;

  private Map<String, Object> attributes = new HashMap<>();

  public DefaultResponse() {
  }

  public DefaultResponse(long requestId) {
    this.requestId = requestId;
  }

  @Override
  public long getRequestId() {
    return requestId;
  }

  @Override
  public void setRequestId(long requestId) {
    this.requestId = requestId;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public Exception getException() {
    return exception;
  }

  @Override
  public void setException(Exception exception) {
    this.exception = exception;
  }

  public boolean hasAttribute(String key) {
    return attributes.containsKey(key);
  }

  public Object getAttribute(String key) {
    return attributes.get(key);
  }

  public void setAttribute(String key, Object value) {
    if (value == null) {
      attributes.remove(key);
    } else {
      attributes.put(key, value);
    }
  }

  public void removeAttribute(String key) {
    attributes.remove(key);
  }


}
