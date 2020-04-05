package com.distkv.common;

import java.io.Serializable;

public enum NodeStatus implements Serializable {
  RUNNING(1),
  DEAD(2);

  private static final long serialVersionUID = -4220017786527146773L;

  private int statusId;

  NodeStatus(int statusId) {
    this.statusId = statusId;
  }

  public int getStatusId() {
    return statusId;
  }
}
