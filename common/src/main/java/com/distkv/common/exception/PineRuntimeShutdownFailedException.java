package com.distkv.common.exception;

public class PineRuntimeShutdownFailedException extends DistkvException {

  protected String key;

  public PineRuntimeShutdownFailedException(String errorMsg) {
    super(errorMsg);
  }

  public String getKey() {
    return key;
  }

}
