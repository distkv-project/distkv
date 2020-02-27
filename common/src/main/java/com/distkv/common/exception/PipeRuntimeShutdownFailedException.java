package com.distkv.common.exception;

public class PipeRuntimeShutdownFailedException extends DistkvException {
  protected String errorCode = ErrorCodeEnum
      .PipeRuntimeShutdownFailedErrorCode
      .getErrorCode();

  protected String key;

  public PipeRuntimeShutdownFailedException(String errorMsg) {
    super(errorMsg);
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
