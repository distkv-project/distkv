package com.distkv.common.exception;

public class DistKVListIndexOutOfBoundsException extends DistKVException {

  protected String errorCode = ErrorCodeEnum
      .DistKVListIndexOutOfBoundsErrorCode
      .getErrorCode();

  protected String key;

  public DistKVListIndexOutOfBoundsException(String key, Exception t) {
    super(String.format("The index is out of bounds of the list %s", key), t);
    this.key = key;
  }

  public DistKVListIndexOutOfBoundsException(String key, String typeCode) {
    super(String.format("The index is out of bounds of the list %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public DistKVListIndexOutOfBoundsException(String key) {
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
