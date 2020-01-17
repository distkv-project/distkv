package com.distkv.dst.common.exception;

/**
 * The exception class to indicates that we have not implemented this.
 */
public class NotImplementException extends RuntimeException {

  public NotImplementException() {
  }

  public NotImplementException(String message) {
    super(message);
  }

}
