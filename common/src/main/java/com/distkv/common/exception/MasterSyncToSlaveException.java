package com.distkv.common.exception;

public class MasterSyncToSlaveException extends DistkvException {
  protected String errorCode = ErrorCodeEnum
      .DistkvErrorCode
      .getErrorCode();

  protected String key;

  public MasterSyncToSlaveException(String key, String typeCode) {
    super(String.format("Master slave synchronization failed. key: %s", key));
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
