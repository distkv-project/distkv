package com.distkv.common.exception;

public class TableAlreadyExistsException extends DistkvException {

  public TableAlreadyExistsException(String tableName) {
    super(String.format("The table %s already exists", tableName));
  }
}
