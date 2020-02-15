package com.distkv.common.exception;

public class DuplicatedPrimaryKeyException extends DistkvException {
  public DuplicatedPrimaryKeyException(String key) {
    super(String.format("Primary key %s is not unique.", key));
  }
}
