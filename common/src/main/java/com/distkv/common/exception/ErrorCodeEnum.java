package com.distkv.common.exception;

public enum ErrorCodeEnum {
  DictKeyNotFoundErrorCode("001"),
  DistkvErrorCode(""),
  PipeRuntimeShutdownFailedErrorCode(""),
  DistkvListIndexOutOfBoundsErrorCode("202"),
  KeyNotFoundErrorCode("100"),
  DistkvKeyDuplicatedErrorCode("200"),
  SortedListMembersDuplicatedErrorCode("008"),
  SortedListIncrScoreOutOfRangeErrorCode("009"),
  SortedListMemberNotFoundErrorCode("006"),
  SortedListTopNumBeNonNegativeErrorCode("007");

  private final String errorcode;

  ErrorCodeEnum(String errorcode) {
    this.errorcode = errorcode;
  }

  public String getErrorCode() {
    return errorcode;
  }

}
