package com.distkv.common.exception;

/**
 * The base exception of DistKV.
 */
public class DistKVException extends RuntimeException {

  String errorCode = ErrorCodeEnum
      .DistKVErrorCode
      .getErrorCode();

  public DistKVException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public DistKVException(String errorCode, String errorMessage, Exception e) {
    super(errorMessage, e);
    this.errorCode = errorCode;
  }

  public DistKVException(String errorMessage) {
    super(errorMessage);
  }

  public DistKVException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public String getErrorCode() {
    return errorCode;
  }
}
