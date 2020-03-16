package com.distkv.core;

public class LogEntry {

  private int logIndex;
  private byte[] value;

  private LogEntry(int logIndex, byte[] value) {
    this.logIndex = logIndex;
    this.value = value;
  }

  public static class LogEntryBuilder {
    private int logIndex;
    private byte[] value;

    public LogEntryBuilder withLogIndex(int logIndex) {
      this.logIndex = logIndex;
      return this;
    }

    public LogEntryBuilder withValue(byte[] value) {
      this.value = value;
      return this;
    }

    public LogEntry build() {
      return new LogEntry(logIndex, value);
    }
  }

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
