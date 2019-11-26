package com.distkv.dst.common.exception;

public class IncorrectRecordFormatException extends DstException {
  public IncorrectRecordFormatException(String tableName) {
    super(String.format("Incorrect record format of table %s", tableName));
  }
}
