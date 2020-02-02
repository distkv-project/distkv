package com.distkv.server.metaserver.server.bean;

import java.io.Serializable;

public class PutKVResponse implements Serializable {
  private static final long serialVersionUID = -4220017786727146673L;

  private boolean success;
  /**
   * redirect peer id
   */
  private String redirect;

  private String errorMsg;

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
