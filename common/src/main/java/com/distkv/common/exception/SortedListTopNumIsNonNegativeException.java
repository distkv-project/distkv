package com.distkv.common.exception;

public class SortedListTopNumIsNonNegativeException extends DistKVException {

  protected String errorCode = ErrorCodeEnum
      .SortedListTopNumBeNonNegativeErrorCode
      .getErrorCode();

  protected String key;

  public SortedListTopNumIsNonNegativeException(String key, int topNum) {
    super(String.format("The topNum must be bigger than 0, your parameter is %d", topNum));
    this.key = key;
  }

  public SortedListTopNumIsNonNegativeException(String key, String typeCode) {
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
