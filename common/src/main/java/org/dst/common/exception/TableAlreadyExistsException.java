package org.dst.common.exception;

public class TableAlreadyExistsException extends DstException {

  public TableAlreadyExistsException(String tableName) {
    super(String.format("The table %s already exists", tableName));
  }
}
