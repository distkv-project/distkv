package com.distkv.common.exception;

public class DistkvUnknownRequestException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .DistkvUnknownRequestErrorCode.getErrorCode();

  protected String key;

  public DistkvUnknownRequestException(String key, Exception e) {
    super(String.format("Unknown Request type for key %s", key), e);
    this.key = key;
  }

  public DistkvUnknownRequestException(String key, String typeCode) {
    super(String.format("Unknown Request Type for key %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public DistkvUnknownRequestException(String key) {
    super(String.format("Unknown Request Type for key %s", key));
    this.key = key;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

  public String getKey() {
    return key;
  }
}
