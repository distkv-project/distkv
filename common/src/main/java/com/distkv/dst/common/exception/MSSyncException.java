package com.distkv.dst.common.exception;

public class MSSyncException extends DstException {
  protected String errorCode = ErrorCodeEnum
      .KeyNotFoundErrorCode
      .getErrorCode();

  protected String key;

  public MSSyncException(String key) {
    super(String.format("Master slave synchronization failed", key));
    this.key = key;
  }

  public MSSyncException(String key, String typeCode) {
    super(String.format("Master slave synchronization failed. key: %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
