package com.distkv.common.exception;

public class SetItemNotFoundException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .SetItemNotFoundErrorCode
      .getErrorCode();

  protected String key;

  public SetItemNotFoundException(String key) {
    super(String.format("The item is not found in the sets %s",  key));
  }

  public SetItemNotFoundException(String key, String typeCode) {
    super(String.format("The item is not found in the sets %s", key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
