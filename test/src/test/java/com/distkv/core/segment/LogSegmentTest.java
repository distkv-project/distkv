package com.distkv.core.segment;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LogSegmentTest {

  @Test
  public void testAddLogEntry() {
    LogSegment logSegment = new LogSegment();
    byte[] value = new byte[] {1, 3, 54, 23};
    logSegment.addLogEntry(value);
    assertEquals(logSegment.getLogEntry(0), value);
  }
}
