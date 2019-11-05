package org.dst.parse.execute;

import org.dst.parse.util.CodeUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseExecute {

  protected String key;
  protected Object value;
  protected String method;
  protected String requestType;

  public Object excute() {
    try {
      String exec = "this." + method + "()";
      Map map = new HashMap<String, Object>();
      map.put("this", this);
      return CodeUtils.executeExpression(exec, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getRequestType() {
    return requestType;
  }
}
