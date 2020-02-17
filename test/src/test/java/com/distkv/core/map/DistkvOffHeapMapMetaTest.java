package com.distkv.core.map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class DistkvOffHeapMapMetaTest {

  private DistkvOffHeapMapMeta meta = new DistkvOffHeapMapMeta();

  @Test
  public void testIsEmpty() {
    assertTrue(meta.isEmpty((byte) 0));
    assertFalse(meta.isEmpty((byte) 13));
  }

  @Test
  public void testIsDeleted() {
    assertTrue(meta.isDeleted((byte) 0b11111110));
    assertFalse(meta.isDeleted((byte) 13));
  }

  @Test
  public void testIsEmptyOrDeleted() {
    assertTrue(meta.isEmptyOrDeleted((byte) 0));
    assertTrue(meta.isEmptyOrDeleted((byte) 0b11111110));

    assertFalse(meta.isEmptyOrDeleted((byte) 12));
  }
}
