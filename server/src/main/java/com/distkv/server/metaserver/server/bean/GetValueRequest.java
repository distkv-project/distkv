package com.distkv.server.metaserver.server.bean;

import java.io.Serializable;

public class GetValueRequest implements Serializable {
  private static final long serialVersionUID = -5623664785560981849L;

  private String path;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
