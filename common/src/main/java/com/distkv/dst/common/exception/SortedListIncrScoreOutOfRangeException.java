package com.distkv.dst.common.exception;

public class SortedListIncrScoreOutOfRangeException extends DstException {

  protected String errorCode = ErrorCodeEnum
      .SortedListIncrScoreOutOfRangeErrorCode
      .getErrorCode();

  protected String key;

  public SortedListIncrScoreOutOfRangeException(String key) {
    super(String.format("The score of the member in the SortedList %s" +
        " will be outing of range after increasing",  key));
  }

  public SortedListIncrScoreOutOfRangeException(String key, String typeCode) {
    super(String.format("The score of the member in the SortedList %s" +
        " will be outing of range after increasing",  key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }

}

