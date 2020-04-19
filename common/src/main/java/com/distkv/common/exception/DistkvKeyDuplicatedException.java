package com.distkv.common.exception;

public class DistkvKeyDuplicatedException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .DistkvKeyDuplicatedErrorCode
      .getErrorCode();

  protected String key;

  public DistkvKeyDuplicatedException(String key) {
    super(String.format("The store has multiple duplicate keys %s", key));
  }

  public DistkvKeyDuplicatedException(String key, String typeCode) {
    super(String.format("The store has multiple duplicate keys %s", key));
    this.key = key;
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }

}
