package org.dst.exception;

public class IncorrectRecordFormatException extends DstException{
  public IncorrectRecordFormatException(String table) {
    super(String.format("record is incorrect format for table %s",table));
  }
  public IncorrectRecordFormatException() {
    super("incorrect records format");
  }
}
