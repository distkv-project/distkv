package com.distkv.dst.common.exception;

/**
 * The exception class to indicates that the key of a dict not found.
 */
public class DictKeyNotFoundException extends DstException {

  protected String errorCode = "001";

  protected String key;

  public DictKeyNotFoundException(String key) {
    super(String.format("The dict key %s not found in the dicts.", key));
    this.key = key;
  }

  public DictKeyNotFoundException(String key, String typeCode) {
    super(String.format("The dict key %s not found in the dicts.", key));
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
