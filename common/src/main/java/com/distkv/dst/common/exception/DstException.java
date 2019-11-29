package com.distkv.dst.common.exception;

/**
 * The base exception of Dst.
 */
public class DstException extends RuntimeException {

  public DstException(String errorMessage) {
    super(errorMessage);
  }

  public DstException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

}
