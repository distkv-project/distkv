package com.distkv.server.metaserver.server.bean;

import java.io.Serializable;

public class PutResponse implements Serializable {
  private static final long serialVersionUID = -4220017786727146673L;

  /**
   * Whether the request is executed successfully.
   */
  private boolean success;
  /**
   * Redirect to another peer id. That means the current peer is not leader,
   * and it redirect to the new leader.
   */
  private String redirect;

  private String errorMessage;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  // TODO(qwang): Use this method on client side.
  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }

  // TODO(qwang)
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
