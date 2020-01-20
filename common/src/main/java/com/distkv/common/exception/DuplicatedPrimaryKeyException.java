package com.distkv.common.exception;

public class DuplicatedPrimaryKeyException extends DistKVException {
  public DuplicatedPrimaryKeyException(String key) {
    super(String.format("Primary key %s is not unique.", key));
  }
}
