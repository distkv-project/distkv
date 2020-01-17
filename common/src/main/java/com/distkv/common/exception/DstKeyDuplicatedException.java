package com.distkv.dst.common.exception;

public class DstKeyDuplicatedException extends DstException {

  protected String errorCode = ErrorCodeEnum
      .DstKeyDuplicatedErrorCode
      .getErrorCode();

  protected String key;

  public DstKeyDuplicatedException(String key) {
    super(String.format("The store has multiple duplicate keys %s", key));
  }

  public DstKeyDuplicatedException(String key, String typeCode) {
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
