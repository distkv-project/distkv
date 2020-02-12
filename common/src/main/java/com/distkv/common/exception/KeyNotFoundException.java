package com.distkv.common.exception;

/**
 * The exception class to indicates that the key not found.
 */
public class KeyNotFoundException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .KeyNotFoundErrorCode
      .getErrorCode();

  protected String key;

  public KeyNotFoundException(String key) {
    super(String.format("The key %s is not found in the store.", key));
    this.key = key;
  }

  public KeyNotFoundException(String key, String typeCode) {
    super(String.format("The key %s is not found in the store.", key));
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
