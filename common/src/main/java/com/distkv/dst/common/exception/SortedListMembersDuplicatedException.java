package com.distkv.dst.common.exception;

public class SortedListMembersDuplicatedException extends DstException {

  protected String errorCode = ErrorCodeEnum
      .SortedListMembersDuplicatedErrorCode
      .getErrorCode();

  protected String key;

  public SortedListMembersDuplicatedException(String key) {
    super(String.format("The SortedList %s which you putted into has " +
        "duplicated members.",  key));
  }

  public SortedListMembersDuplicatedException(String key, String typeCode) {
    super(String.format("The SortedList %s which you putted into has " +
        "duplicated members.",  key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }

}