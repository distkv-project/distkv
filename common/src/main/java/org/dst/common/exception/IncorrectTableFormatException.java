package org.dst.common.exception;

public class IncorrectTableFormatException extends DstException {
  public IncorrectTableFormatException(String tableName) {
    super(String.format("Incorrect specification format of table %s", tableName));
  }
}
