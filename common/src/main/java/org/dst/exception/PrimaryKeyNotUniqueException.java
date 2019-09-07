package org.dst.exception;

public class PrimaryKeyNotUniqueException extends DstException{
  public PrimaryKeyNotUniqueException() {
    super(String.format("primary key is not unique"));
  }
}
