package com.distkv.common.exception;

public class IncorrectRecordFormatException extends DistKVException {
  public IncorrectRecordFormatException(String tableName) {
    super(String.format("Incorrect record format of table %s", tableName));
  }
}
