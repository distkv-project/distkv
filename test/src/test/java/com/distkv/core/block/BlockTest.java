package com.distkv.core.block;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class BlockTest {

  @Test
  public void testNonFixedValue() {
    Block block = new Block(30);
    byte[] value1 = new byte[] {123, 23};
    byte[] value2 = new byte[] {12, 23};

    block.addNonFixedValue(value1);
    block.addNonFixedValue(value2);

    assertEquals(block.readNonFixedValue(0), value1);
    assertEquals(block.readNonFixedValue(1), value2);
  }

  @Test
  public void testTwoNonFixedValue() {
    Block block = new Block(50);
    byte[] key1 = new byte[] {12, 34, 35};
    byte[] value1 = new byte[] {21, 124, 23, 23};
    block.addTwoNonFixedValue(key1, value1);
    byte[][] result1 = block.readTwoNonFixedValues(0);
    assertEquals(result1[0], key1);
    assertEquals(result1[1], value1);

    byte[] key2 = new byte[] {23, 16, 12};
    byte[] value2 = new byte[] {123, 23, 12, 2, 32, 24};
    block.addTwoNonFixedValue(key2, value2);
    byte[][] result2 = block.readTwoNonFixedValues(2);
    assertEquals(result2[0], key2);
    assertEquals(result2[1], value2);
  }
}
