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
    logSegment.appendLogEntry(new LogEntry(0, value));
    assertEquals(logSegment.getLogEntry(0).getValue(), value);
  }

  @Test
  public void testValueAcrossTwoBlocks() {
    LogSegment logSegment = new LogSegment();
    byte[] value = new byte[BlockPool.getInstance().getBlockSize() - 2];
    logSegment.appendValue(value);
    byte[] value2 = new byte[] {1, 23, 45, 26};
    logSegment.appendValue(value2);
    assertEquals(logSegment.getLogEntry(1).getValue(), value2);
  }
}
