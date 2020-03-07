package com.distkv.common.exception;

public class PineLikerLikeeNotFoundException extends DistkvException {
  protected String errorCode = ErrorCodeEnum
      .PineRuntimeShutdownFailedErrorCode
      .getErrorCode();

  protected String key;

  public PineLikerLikeeNotFoundException(String errorMsg) {
    super(errorMsg);
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
