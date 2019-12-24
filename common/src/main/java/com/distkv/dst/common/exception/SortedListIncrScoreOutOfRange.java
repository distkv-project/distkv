package com.distkv.dst.common.exception;

public class SortedListIncrScoreOutOfRange extends DstException {

  protected String errorCode = "009";

  protected String key;

  public SortedListIncrScoreOutOfRange(String key) {
    super(String.format("The score of the member in the SortedList %s" +
        " will be outing of range after increasing",  key));
  }

  public SortedListIncrScoreOutOfRange(String key, String typeCode) {
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

