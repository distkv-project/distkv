package org.dst.exception;

public class DuplicatedPrimaryKeyException extends DstException {
  public DuplicatedPrimaryKeyException() {
    super(String.format("primary key is not unique"));
  }
}
