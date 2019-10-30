package org.dst.rpc.transport.api.async;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Request {

  private long requestId;

  private String interfaceName;

  private String methodName;

  private String argsType;

  private Object[] argsValue;

  private Map<String, Object> attributes = new HashMap<>();

  public long getRequestId() {
    return requestId;
  }

  public void setRequestId(long requestId) {
    this.requestId = requestId;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getArgsType() {
    return argsType;
  }

  public void setArgsType(String argsType) {
    this.argsType = argsType;
  }

  public Object[] getArgsValue() {
    return argsValue;
  }

  public void setArgsValue(Object[] argsValue) {
    this.argsValue = argsValue;
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
