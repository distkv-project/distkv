package org.dst.common.exception;

public class DuplicatedPrimaryKeyException extends DstException {
  public DuplicatedPrimaryKeyException() {
    super(String.format("primary key is not unique"));
  }
}
