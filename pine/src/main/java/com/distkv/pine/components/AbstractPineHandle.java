package com.distkv.pine.components;

import com.distkv.common.id.PineHandleId;

/**
 * The abstract class `PineHandle` is used to represent
 * a handle of the Pine component.
 */
public abstract class AbstractPineHandle {

  private PineHandleId handleId;

  protected AbstractPineHandle() {
    handleId = PineHandleId.fromRandom();
  }

  protected String getKey() {
    /// Note that the key of Pine component is used a prefix that is the component type.
    /// And the suffix of the key the handle id of the component.
    return String.format("%s_%s", getComponentType(), handleId.hex());
  }

  protected abstract String getComponentType();

}
