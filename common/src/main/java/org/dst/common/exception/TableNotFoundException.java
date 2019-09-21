package org.dst.exception;

public class TableNotFoundException extends DstException {
  public TableNotFoundException(String tableName) {
    super(String.format("The table %s not exists in store", tableName));
  }
}
