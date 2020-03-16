package com.distkv.core.segment;

import com.distkv.core.LogEntry;
import com.distkv.core.block.BlockPool;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LogSegmentTest {

  @Test
  public void testAddLogEntry() {
    LogSegment logSegment = new LogSegment();
    byte[] value = new byte[] {1, 3, 54, 23};
    LogEntry logEntry = new LogEntry.LogEntryBuilder()
        .withLogIndex(0)
        .withValue(value)
        .build();
    logSegment.appendLogEntry(logEntry);
    assertEquals(logSegment.getLogEntry(0).getValue(), value);
  }

  @Test
  public void testValueAcrossTwoBlocks() {
    LogSegment logSegment = new LogSegment();
    byte[] value = new byte[BlockPool.getInstance().getBlockSize() - 2];
    logSegment.appendLogEntry(new LogEntry.LogEntryBuilder()
        .withValue(value)
        .withLogIndex(0).build());

    byte[] value2 = new byte[] {1, 23, 45, 26};
    logSegment.appendLogEntry(new LogEntry.LogEntryBuilder()
        .withValue(value2)
        .withLogIndex(1).build());
    assertEquals(logSegment.getLogEntry(1).getValue(), value2);
  }

  @Test
  public void testClear() {
    LogSegment logSegment = new LogSegment();
    for (int i = 0; i < BlockPool.getInstance().getBlockSize(); i++) {
      logSegment.appendLogEntry(new LogEntry.LogEntryBuilder()
          .withLogIndex(logSegment.getSize())
          .withValue(new byte[] {12, 23, 45, 23})
          .build());
    }
    byte[] value = new byte[] {36};
    logSegment.appendLogEntry(new LogEntry.LogEntryBuilder()
        .withLogIndex(logSegment.getSize())
        .withValue(value)
        .build());
    logSegment.clear(logSegment.getSize() - 1);
    assertEquals(logSegment.getLogEntry(logSegment.getSize() - 1).getValue(), value);

    value = new byte[] {37, 23};
    logSegment.appendLogEntry(new LogEntry.LogEntryBuilder()
        .withLogIndex(logSegment.getSize())
        .withValue(value)
        .build());
    assertEquals(logSegment.getLogEntry(logSegment.getSize() - 1).getValue(), value);
  }
}
