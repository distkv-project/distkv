package com.distkv.dst.common.exception;

/**
 * The base exception of Dst.
 */
public class DstException extends RuntimeException {

  String errorCode = "";

  public DstException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public DstException(String errorCode, String errorMessage, Exception e) {
    super(errorMessage, e);
    this.errorCode = errorCode;
  }

  public DstException(String errorMessage) {
    super(errorMessage);
  }

  public DstException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public String getErrorCode() {
    return errorCode;
  }
}
