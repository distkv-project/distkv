package com.distkv.common.exception;

public class MasterSyncToSlaveException extends DistKVException {
  protected String errorCode = ErrorCodeEnum
      .DistKVErrorCode
      .getErrorCode();

  protected String key;

  public MasterSyncToSlaveException(String key) {
    super(String.format("Master slave synchronization failed", key));
    this.key = key;
  }

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
