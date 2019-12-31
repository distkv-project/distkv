package com.distkv.dst.common.exception;

public class SortedListTopNumBeNonNegativeException extends DstException {

  protected String errorCode = ErrorCodeEnum
      .SortedListTopNumBeNonNegativeErrorCode
      .getErrorCode();

  protected String key;

  public SortedListTopNumBeNonNegativeException(String key, int topNum) {
    super(String.format("The topNum must be bigger than 0, your parameter is %d", topNum));
    this.key = key;
  }

  public SortedListTopNumBeNonNegativeException(String key, String typeCode) {
    super("The topNum must be bigger than 0");
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return this.key;
  }

  public String getErrorCode() {
    return this.errorCode;
  }
}
