package org.dst.common.exception;

public class TableNotFoundException extends DstException {
  public TableNotFoundException(String tableName) {
    super(String.format("Table %s not found.", tableName));
  }
}
