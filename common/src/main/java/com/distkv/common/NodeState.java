package com.distkv.common;

import java.io.Serializable;

public enum NodeState implements Serializable {
  STARTING(0),
  RUNNING(1),
  DEAD(2);

  private static final long serialVersionUID = -4220017786527146773L;

  private int statusCode;

  NodeState(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
