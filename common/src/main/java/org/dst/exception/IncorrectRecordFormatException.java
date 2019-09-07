package org.dst.exception;

public class IncorrectRecordFormatException extends DstException {
  public IncorrectRecordFormatException(String tableName) {
    super(String.format("record is incorrect format for table %s", tableName));
  }
}
