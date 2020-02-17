package com.distkv.core.block;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BlockTest {

  @Test
  public void testFixed() {
    Block block = new Block(20);
    byte[] value1 = new byte[] {1, 23, 24};
    block.write(0, value1);
    assertEquals(block.read(0, 3), value1);
  }

  @Test
  public void testNonFixedValue() {
    Block block = new Block(30);
    byte[] value1 = new byte[] {123, 23};
    byte[] value2 = new byte[] {12, 23};
    block.writeNonFixedValue(value1);
    block.writeNonFixedValue(value2);
    assertEquals(block.readNonFixedValue(0), value1);
    assertEquals(block.readNonFixedValue(1), value2);
  }

  @Test
  public void testTwoNonFixedValue() {
    Block block = new Block(50);
    byte[] key1 = new byte[] {12, 34, 35};
    byte[] value1 = new byte[] {21, 124, 23, 23};
    block.writeTwoNonFixedValue(key1, value1);
    byte[][] result1 = block.readTwoNonFixedValues(0);
    assertEquals(result1[0], key1);
    assertEquals(result1[1], value1);

    byte[] key2 = new byte[] {23, 16, 12};
    byte[] value2 = new byte[] {123, 23, 12, 2, 32, 24};
    block.writeTwoNonFixedValue(key2, value2);
    byte[][] result2 = block.readTwoNonFixedValues(2);
    assertEquals(result2[0], key2);
    assertEquals(result2[1], value2);
  }

  @Test
  public void testTwoNonFixedValueOverFlow() {
    Block block = new Block(20);
    byte[] key1 = new byte[] {1};
    byte[] value1 = new byte[] {5};
    block.writeTwoNonFixedValue(key1, value1);
    int available = block.writeTwoNonFixedValue(key1, value1);
    assertEquals(available, 0);

    block = new Block(20);
    block.writeTwoNonFixedValue(key1, value1);
    value1 = new byte[] {5, 6};
    available = block.writeTwoNonFixedValue(key1, value1);
    assertEquals(available, -1);
  }

  @Test
  public void testNonFixedValueOverFlow() {
    Block block = new Block(20);
    byte[] value1 = new byte[] {1, 2, 3, 4, 5, 6};
    block.writeNonFixedValue(value1);
    int available = block.writeNonFixedValue(value1);
    assertEquals(available, 0);

    block = new Block(20);
    block.writeNonFixedValue(value1);
    value1 = new byte[] {1, 2, 3, 4, 5, 6, 7};
    available = block.writeNonFixedValue(value1);
    assertEquals(available, -1);
  }
}
