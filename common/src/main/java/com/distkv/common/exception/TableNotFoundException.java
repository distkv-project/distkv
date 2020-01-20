package com.distkv.common.exception;

public class TableNotFoundException extends DistKVException {
  public TableNotFoundException(String tableName) {
    super(String.format("Table %s not found.", tableName));
  }
}
