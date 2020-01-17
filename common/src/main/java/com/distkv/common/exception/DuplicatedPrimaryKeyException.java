package com.distkv.dst.common.exception;

public class DuplicatedPrimaryKeyException extends DstException {
  public DuplicatedPrimaryKeyException(String key) {
    super(String.format("Primary key %s is not unique.", key));
  }
}
