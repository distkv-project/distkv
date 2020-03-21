package com.distkv.common.exception;

public class DistkvWrongRequestFormatException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .DistkvWrongRequestFormatErrorCode.getErrorCode();

  protected String key;

  public DistkvWrongRequestFormatException(String key, Exception e) {
    super(String.format("the wrong request format for key %s", key), e);
    this.key = key;
  }

  public DistkvWrongRequestFormatException(String key, String typeCode) {
    super(String.format("the wrong request format for key %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

  public String getKey() {
    return key;
  }
}
