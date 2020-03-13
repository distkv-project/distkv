package com.distkv.core;

public class LogEntry {

  public LogEntry(int logIndex, byte[] value) {
    this.logIndex = logIndex;
    this.value = value;
  }

  private int logIndex;
  private byte[] value;

  public int getLogIndex() {
    return logIndex;
  }

  public void setLogIndex(int logIndex) {
    this.logIndex = logIndex;
  }

  public byte[] getValue() {
    return value;
  }

  public void setValue(byte[] value) {
    this.value = value;
  }
}
