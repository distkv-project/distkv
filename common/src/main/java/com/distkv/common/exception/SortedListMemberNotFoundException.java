package com.distkv.common.exception;

public class SortedListMemberNotFoundException extends DistKVException {

  protected String errorCode = ErrorCodeEnum
      .SortedListMemberNotFoundErrorCode
      .getErrorCode();

  protected String key;

  public SortedListMemberNotFoundException(String key) {
    super(String.format("The member is not found in the SortedList %s",  key));
  }

  public SortedListMemberNotFoundException(String key, String typeCode) {
    super(String.format("The member  is not found in the SortedList %s", key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
