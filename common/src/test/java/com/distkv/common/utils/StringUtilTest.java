package com.distkv.common.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StringUtilTest {
  @Test
  public void testIsNullOrEmpty() {
    boolean isEmpty = StringUtil.isNullOrEmpty("aaa");
    Assert.assertFalse(isEmpty);
  }

}
