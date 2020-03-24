package com.distkv.common.exception;

/**
 * The base exception of DistKV.
 */
public class DistkvException extends RuntimeException {

  String errorCode = ErrorCodeEnum
      .DistkvErrorCode
      .getErrorCode();

  public DistkvException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public DistkvException(String errorMessage) {
    super(errorMessage);
  }
}
