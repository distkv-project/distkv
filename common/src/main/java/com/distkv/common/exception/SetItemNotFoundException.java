package com.distkv.common.exception;

public class SetItemNotFoundException extends DistkvException {

  protected String errorCode = ErrorCodeEnum
      .SetItemNotFoundErrorCode
      .getErrorCode();

  protected String key;

  protected String itemName;

  public SetItemNotFoundException(String key, String itemName) {
    super(String.format("The item %s is not found in the set %s",  itemName, key));
  }

  public SetItemNotFoundException(String key, String itemName, String typeCode) {
    super(String.format("The item %s is not found in the set %s", itemName, key));
    this.errorCode = typeCode + this.errorCode;
  }

  public String getKey() {
    return key;
  }

  public String getItemName() {
    return itemName;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
