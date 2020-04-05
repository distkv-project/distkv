package com.distkv.server.metaserver.server.bean;

import com.distkv.server.view.NodeTable;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class GetGlobalViewResponse implements Serializable {

  private static final long serialVersionUID = -7220117786727166673L;

  private ConcurrentHashMap<String, NodeTable> globalView;

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

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public ConcurrentHashMap<String, NodeTable> getGlobalView() {
    return globalView;
  }

  public void setGlobalView(ConcurrentHashMap<String, NodeTable> globalView) {
    this.globalView = globalView;
  }
}
