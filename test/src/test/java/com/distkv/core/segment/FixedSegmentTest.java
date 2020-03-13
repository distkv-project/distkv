package com.distkv.core.segment;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FixedSegmentTest {

  @Test
  public void testBase() {
    FixedValueSegment segment = new FixedValueSegment(1, 7);
    byte[] value1 = new byte[] {1, 23, 34, 45, 23, 12, 23};
    int pointer = segment.putFixedValue(0, value1);
    assertEquals(segment.getFixedValue(pointer), value1);

    value1 = new byte[] {13, 23, 34, 15, 21, 12, 23};
    pointer = segment.putFixedValue(1, value1);
    assertEquals(segment.getFixedValue(pointer), value1);
  }

  @Test
  public void testSegmentWithBlocks() {
    FixedValueSegment segment = new FixedValueSegment(1, 7);
    byte[] value1 = new byte[] {1, 23, 34, 45, 23, 12, 23};
    for (int i = 0; i < 1024 * 157; i++) {
      segment.putFixedValue(0, value1);
    }
    int pointer = segment.putFixedValue(segment.getSize(), value1);
    assertEquals(segment.getFixedValue(pointer), value1);
  }

  @Test
  public void testSegment() {
    IntSegment intSegment = new IntSegment(1);
    ShortSegment shortSegment = new ShortSegment(1);
    LongSegment longSegment = new LongSegment(1);
    FloatSegment floatSegment = new FloatSegment(1);
    DoubleSegment doubleSegment = new DoubleSegment(1);
    ByteSegment byteSegment = new ByteSegment(1);

    for (int i = 0; i < 100; i++) {
      intSegment.put(i, i);
      shortSegment.put(i, (short) i);
      longSegment.put(i, i);
      floatSegment.put(i, i);
      doubleSegment.put(i, i);
      byteSegment.putValue(i, (byte) i);
    }

    for (int i = 0; i < 100; i++) {
      assertEquals(i, intSegment.get(i));
      assertEquals(i, shortSegment.get(i));
      assertEquals(i, longSegment.get(i));
      assertEquals(i, floatSegment.get(i));
      assertEquals(i, doubleSegment.get(i));
      assertEquals(i, byteSegment.getValue(i));
    }
  }
}
