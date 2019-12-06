package com.distkv.dmeta.server.statemachine;

public class DataSpace implements SpaceInterface {

  private String value;

  public DataSpace(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public SpaceType getType() {
    return SpaceType.DATA_SPACE;
  }
}
