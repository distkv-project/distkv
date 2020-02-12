package com.distkv.common.exception;

public class DistkvListIndexOutOfBoundsException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .DistkvListIndexOutOfBoundsErrorCode
      .getErrorCode();

  protected String key;

  public DistkvListIndexOutOfBoundsException(String key, Exception t) {
    super(String.format("The index is out of bounds of the list %s", key), t);
    this.key = key;
  }

  public DistkvListIndexOutOfBoundsException(String key, String typeCode) {
    super(String.format("The index is out of bounds of the list %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public DistkvListIndexOutOfBoundsException(String key) {
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
