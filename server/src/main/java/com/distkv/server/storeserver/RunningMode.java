package com.distkv.server.storeserver;

public enum RunningMode {
  STANDALONE("standalone"),
  DISTRIBUTED("distributed");

  private String modeString;

  RunningMode(String mode) {
    this.modeString = mode;
  }

  @Override
  public String toString() {
    return modeString;
  }
}
