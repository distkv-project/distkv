package com.distkv.common.exception;

public class SetKeyNotFoundException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .SetKeyNotFoundErrorCode
      .getErrorCode();

  protected String key;

  public SetKeyNotFoundException(String key) {
    super(String.format("The key is not found in the sets %s",  key));
  }

  public SetKeyNotFoundException(String key, String typeCode) {
    super(String.format("The key is not found in the sets %s", key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
