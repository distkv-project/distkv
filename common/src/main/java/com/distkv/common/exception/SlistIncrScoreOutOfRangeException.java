package com.distkv.common.exception;

public class SlistIncrScoreOutOfRangeException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .SlistIncrScoreOutOfRangeErrorCode
      .getErrorCode();

  protected String key;

  public SlistIncrScoreOutOfRangeException(String key) {
    super(String.format("The score of the member in the SortedList %s" +
        " will be outing of range after increasing",  key));
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }

}
