package com.distkv.common.exception;

public enum ErrorCodeEnum {
  DictKeyNotFoundErrorCode("001"),
  DistkvErrorCode(""),
  DistkvListIndexOutOfBoundsErrorCode("202"),
  KeyNotFoundErrorCode("100"),
  DistkvKeyDuplicatedErrorCode("200"),
  SlistMembersDuplicatedErrorCode("008"),
  SlistIncrScoreOutOfRangeErrorCode("009"),
  SlistMemberNotFoundErrorCode("006"),
  SlistTopNumBeNonNegativeErrorCode("007"),
  SetItemNotFoundErrorCode("010");

  private final String errorcode;

  ErrorCodeEnum(String errorcode) {
    this.errorcode = errorcode;
  }

  public String getErrorCode() {
    return errorcode;
  }

}
