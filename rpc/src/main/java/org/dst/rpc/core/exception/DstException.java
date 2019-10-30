package org.dst.rpc.core.exception;


public class DstException extends RuntimeException {

  public DstException() {
  }

  public DstException(String message) {
    super(message);
  }

  public DstException(String message, Throwable cause) {
    super(message, cause);
  }

  public DstException(Throwable cause) {
    super(cause);
  }

  public DstException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
