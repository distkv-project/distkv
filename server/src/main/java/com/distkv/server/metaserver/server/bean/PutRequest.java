package com.distkv.server.metaserver.server.bean;

import java.io.Serializable;

public class PutRequest implements Serializable {

  private static final long serialVersionUID = -5623664785560971849L;

  private String key;

  private String value;

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
