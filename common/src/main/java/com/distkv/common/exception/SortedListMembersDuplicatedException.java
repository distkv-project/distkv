package com.distkv.common.exception;

public class SortedListMembersDuplicatedException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .SlistMembersDuplicatedErrorCode
      .getErrorCode();

  protected String key;

  public SortedListMembersDuplicatedException(String key) {
    super(String.format("The SortedList %s which you putted into has " +
        "duplicated members.",  key));
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }

}
