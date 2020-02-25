package com.distkv.core;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static java.util.Map.Entry;

public class DistkvNavigableMapImplTest {

  private DistkvNavigableMap<Integer, String> map;
  private static final int FIRST_KEY = 1;
  private static final int LAST_KEY = 2;
  private static final String FIRST_VALUE = "TEST1";
  private static final String LAST_VALUE = "TEST1";

  @BeforeTest
  public void before() {
    map = new DistkvNavigableMapImpl<>();
    map.put(FIRST_KEY, FIRST_VALUE);
    map.put(LAST_KEY, LAST_VALUE);
  }

  @Test
  public void testFirstKey() {
    int firstKey = map.firstKey();
    assertEquals(firstKey, FIRST_KEY);
  }

  @Test
  public void testLastKey() {
    int lastKey = map.lastKey();
    assertEquals(lastKey, LAST_KEY);
  }

  @Test
  public void testFirstEntry() {
    Entry firstEntry = map.firstEntry();
    assertEquals(firstEntry.getKey(), FIRST_KEY);
    assertEquals(firstEntry.getValue(), FIRST_VALUE);
  }

  @Test
  public void testLastEntry() {
    Entry lastEntry = map.lastEntry();
    assertEquals(lastEntry.getKey(), LAST_KEY);
    assertEquals(lastEntry.getValue(), LAST_VALUE);
  }

  @Test
  public void testLowerKey() {
    int lastKey = map.lastKey();
    int lowerKey = map.lowerKey(lastKey);
    assertEquals(lowerKey, FIRST_KEY);
  }

  @Test
  public void testHigherKey() {
    int firstKey = map.firstKey();
    int higherKey = map.higherKey(firstKey);
    assertEquals(higherKey, LAST_KEY);
  }

  @Test
  public void testLowerEntry() {
    int lastKey = map.lastKey();
    Entry firstEntry = map.lowerEntry(lastKey);
    assertEquals(firstEntry.getKey(), FIRST_KEY);
    assertEquals(firstEntry.getValue(), FIRST_VALUE);
  }

  @Test
  public void testHigherEntry() {
    int firstKey = map.firstKey();
    Entry higherEntry = map.higherEntry(firstKey);
    assertEquals(higherEntry.getKey(), LAST_KEY);
    assertEquals(higherEntry.getValue(), LAST_VALUE);
  }

}
