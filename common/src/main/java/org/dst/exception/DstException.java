package org.dst.exception;

public class DstException extends RuntimeException {

  public DstException(String errorMessage) {
    super(errorMessage);
  }

  public DstException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

}
