package com.distkv.common.exception;

public class DistKVKeyDuplicatedException extends DistKVException {

  protected String errorCode = ErrorCodeEnum
      .DistKVKeyDuplicatedErrorCode
      .getErrorCode();

  protected String key;

  public DistKVKeyDuplicatedException(String key) {
    super(String.format("The store has multiple duplicate keys %s", key));
  }

  public DistKVKeyDuplicatedException(String key, String typeCode) {
    super(String.format("The store has multiple duplicate keys %s", key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getErrorCode() {
    return errorCode;
  }

}
