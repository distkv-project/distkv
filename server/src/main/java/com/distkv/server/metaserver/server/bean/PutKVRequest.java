package com.distkv.dmeta.server.bean;

import java.io.Serializable;

public class PutKVRequest implements Serializable {
  private static final long serialVersionUID = -5623664785560971849L;

  private String key;

  private String value;

  private String path;

  private int type;

  public int getType() {
    return type;
  }

  // type = 1 means putKV to path
  // type = 2 means createNameSpace
  public void setType(int type) {
    this.type = type;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
