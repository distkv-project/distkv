package com.distkv.common.exception;

public enum ErrorCodeEnum {
  DictKeyNotFoundErrorCode("001"),
  DistkvErrorCode(""),
  DistkvUnknownRequestErrorCode("002"),
  DistkvWrongRequestFormatErrorCode("003"),
  DistkvListIndexOutOfBoundsErrorCode("202"),
  KeyNotFoundErrorCode("100"),
  DistkvKeyDuplicatedErrorCode("200"),
  SortedListMembersDuplicatedErrorCode("008"),
  SortedListIncrScoreOutOfRangeErrorCode("009"),
  SortedListMemberNotFoundErrorCode("006"),
  SortedListTopNumBeNonNegativeErrorCode("007"),
  SetItemNotFoundErrorCode("010");

  private final String errorcode;

  ErrorCodeEnum(String errorcode) {
    this.errorcode = errorcode;
  }

  public String getErrorCode() {
    return errorcode;
  }

}
