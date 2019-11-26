package com.distkv.dst.common.exception;

public class DstListIndexOutOfBoundsException extends DstException {

  protected String key;

  public DstListIndexOutOfBoundsException(String key, Exception t) {
    super(String.format("The index is out of bounds of the list %s", key), t);
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
