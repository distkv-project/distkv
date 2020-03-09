package com.distkv.common.exception;

public class PineLikerLikeeNotFoundException extends DistkvException {

  protected String key;

  public PineLikerLikeeNotFoundException(String errorMsg) {
    super(errorMsg);
  }

  public String getKey() {
    return key;
  }

}
