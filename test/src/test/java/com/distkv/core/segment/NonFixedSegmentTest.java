package com.distkv.core.segment;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NonFixedSegmentTest {

  @Test
  public void testValue() {
    NonFixedSegment segment = new NonFixedSegment(1);
    byte[] value1 = new byte[] {1, 3, 6, 98, 12, 23, 45};
    int pointer1 = segment.addValue(value1);
    assertEquals(segment.getValue(pointer1), value1);

    byte[] value2 = new byte[] {2, 32, 45, 67, 23, 35, 67, 23, 24, 56, 34, 65, 23};
    int pointer2 = segment.addValue(value2);
    assertEquals(segment.getValue(pointer2), value2);
  }

  @Test
  public void testKeyValue() {
    NonFixedSegment segment = new NonFixedSegment(1);
    byte[] key1 = new byte[] {23, 23, 56, 67, 23};
    byte[] value1 = new byte[] {1, 3, 6, 98, 12, 23, 45};
    int pointer1 = segment.addKeyValue(key1, value1);
    assertEquals(segment.getKeyValue(pointer1), new byte[][] {key1, value1});

    byte[] key2 = new byte[] {12, 45, 67, 78, 97};
    byte[] value2 = new byte[] {2, 32, 45, 67, 23, 35, 67, 23, 24, 56, 34, 65, 23};
    int pointer2 = segment.addKeyValue(key2, value2);
    assertEquals(segment.getKeyValue(pointer2), new byte[][] {key2, value2});
  }

  @Test
  public void testResize() {
    NonFixedSegment segment = new NonFixedSegment(1);
    byte[] key1 = new byte[] {12, 45, 67, 78, 97};
    byte[] value1 = new byte[] {2, 32, 45, 67, 23, 35, 67, 23, 24, 56, 34, 65, 23};
    int iterator = 1024 * 712;
    for (int i = 0; i < iterator; i++) {
      segment.addKeyValue(key1, value1);
    }
    assertEquals(segment.getKeyValue(iterator - 43), new byte[][] {key1, value1});

    segment = new NonFixedSegment(1);
    for (int i = 0; i < iterator; i++) {
      segment.addValue(value1);
    }
    assertEquals(segment.getValue(iterator - 43), value1);
  }
}
