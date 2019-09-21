package org.dst.common.exception;

public class RepeatCreateTableException extends DstException {

  public RepeatCreateTableException(String tableName) {
    super(String.format("The table %s already exists in store", tableName));
  }
}
