package com.distkv.common.exception;

public class SlistMemberNotFoundException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .SlistMemberNotFoundErrorCode
      .getErrorCode();

  protected String key;

  public SlistMemberNotFoundException(String key) {
    super(String.format("The member is not found in the SortedList %s",  key));
  }

  public SlistMemberNotFoundException(String key, String typeCode) {
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
