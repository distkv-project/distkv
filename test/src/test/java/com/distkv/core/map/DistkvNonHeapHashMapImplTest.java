package com.distkv.core.map;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DistkvNonHeapHashMapImplTest {

  @Test
  public void testBaseOperation() {
    DistkvNonHeapHashMapImpl map = new DistkvNonHeapHashMapImpl();
    byte[] key1 = new byte[] {12, 34, 35};
    byte[] value1 = new byte[] {21, 124, 23, 23};
    map.put(key1, value1);
    assertEquals(value1, map.get(key1));

    byte[] key2 = new byte[] {23, 16, 12};
    byte[] value2 = new byte[] {123, 23, 12, 2, 32, 24};
    map.put(key2, value2);
    assertEquals(value2, map.get(key2));
  }
}
