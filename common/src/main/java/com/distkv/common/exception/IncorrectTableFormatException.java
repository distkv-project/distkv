package com.distkv.common.exception;

public class IncorrectTableFormatException extends DistkvException {
  public IncorrectTableFormatException(String tableName) {
    super(String.format("Incorrect specification format of table %s", tableName));
  }
}
