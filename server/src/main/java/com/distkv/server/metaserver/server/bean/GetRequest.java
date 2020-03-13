package com.distkv.server.metaserver.server.bean;

import java.io.Serializable;

public class GetRequest implements Serializable {
  private static final long serialVersionUID = -5623664785560981849L;

  private String key;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
