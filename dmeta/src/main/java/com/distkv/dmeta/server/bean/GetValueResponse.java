package com.distkv.dmeta.server.bean;

import java.io.Serializable;

public class GetValueResponse implements Serializable {

  private static final long serialVersionUID = -4220017686727146673L;

  private String value;

  private boolean success;
  /**
   * redirect peer id
   */
  private String redirect;

  private String errorMsg;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
