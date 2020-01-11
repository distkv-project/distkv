package com.distkv.dst.common.exception;

public class DstListIndexOutOfBoundsException extends DstException {

  protected String errorCode = ErrorCodeEnum
      .DstListIndexOutOfBoundsErrorCode
      .getErrorCode();

  protected String key;

  public DstListIndexOutOfBoundsException(String key, Exception t) {
    super(String.format("The index is out of bounds of the list %s", key), t);
    this.key = key;
  }

  public DstListIndexOutOfBoundsException(String key, String typeCode) {
    super(String.format("The index is out of bounds of the list %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public DstListIndexOutOfBoundsException(String key) {
    super(String.format("The index is out of bounds of the list %s", key));
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
