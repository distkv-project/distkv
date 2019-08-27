package org.dst.exception;

public class TableNotFoundException extends DstException{
  public TableNotFoundException(String errorMessage) {
    super(String.format("The table %s not exists in store", errorMessage));
  }
}
