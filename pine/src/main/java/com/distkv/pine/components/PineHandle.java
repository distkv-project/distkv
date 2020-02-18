package com.distkv.pine.components;

import com.distkv.common.id.PineHandleId;

/**
 * The abstract class `PineHandle` is used to represent a handle
 * of the Pine component.
 */
public abstract class PineHandle {

  private PineHandleId handleId;

  protected PineHandle() {
    handleId = PineHandleId.fromRandom();
  }

  protected String getKey() {
    return String.format("%s_%s", getComponentType(), handleId.hex());
  }

  protected abstract String getComponentType();

}
