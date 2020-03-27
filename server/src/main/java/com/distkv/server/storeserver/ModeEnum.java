package com.distkv.server.storeserver;

public enum ModeEnum {
  STANDALONE("standalone"),
  DISTRIBUTED("distributed");

  private String modeString;

  ModeEnum(String mode) {
    this.modeString = mode;
  }

  @Override
  public String toString() {
    return modeString;
  }
}
