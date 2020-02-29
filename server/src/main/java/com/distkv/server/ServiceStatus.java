package com.distkv.server;

public enum ServiceStatus {
  UNINITED(0, "UNINITED"),
  INITED(1, "INITED"),
  RUNNING(2, "RUNNING"),
  STOPPED(3, "STOPPED");

  private final String stateName;
  private final int value;

  ServiceStatus(int value, String stateName) {
    this.stateName = stateName;
    this.value = value;
  }
}
