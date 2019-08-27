package org.dst.exception;

public class RepeatCreateTableException extends DstException {

  public RepeatCreateTableException(String errorMessage) {
    super(String.format("The table %s already exists in store", errorMessage));
  }
}
