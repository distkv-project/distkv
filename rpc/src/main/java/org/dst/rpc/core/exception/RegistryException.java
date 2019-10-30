package org.dst.rpc.core.exception;


public class RegistryException extends RuntimeException {

  public RegistryException() {
  }

  public RegistryException(String message) {
    super(message);
  }

  public RegistryException(String message, Throwable cause) {
    super(message, cause);
  }

  public RegistryException(Throwable cause) {
    super(cause);
  }

  public RegistryException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
