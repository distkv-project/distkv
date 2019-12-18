package com.distkv.dst.common.exception;

public class SortedListTopNumBePositiveException extends DstException {

  protected String errorCode = "007";

  protected String key;

  public SortedListTopNumBePositiveException(String key, int topNum) {
    super(String.format("The topNum must be bigger than 0, your parameter is %d", topNum));
    this.key = key;
  }

  public SortedListTopNumBePositiveException(String key, String typeCode) {
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
