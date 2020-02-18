package com.distkv.pine.components;

public abstract class PineHandle {

  private PineHandleId handleId;

  protected String getKey() {
    return String.format("%s_%s", getComponentType(), handleId.toString());
  }

  protected abstract String getComponentType();
}
