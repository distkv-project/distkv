package com.distkv.server;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddressTest {

  @Test
  public void testDefaultAddress() {
    Address address = Address.defaultAddress();
    assertTrue(StringUtils.isNotBlank(address.getHost()));
    assertEquals(address.getPort(), 8082);
  }

  @Test
  public void  testFrom() {
    Address address = Address.from(8081);
    assertTrue(StringUtils.isNotBlank(address.getHost()));
    assertEquals(address.getPort(), 8081);

    address = Address.from("127.0.0.1");
    assertEquals(address.getHost(), "127.0.0.1");
    assertEquals(address.getPort(), 8082);

    address = Address.from("127.0.0.1", 8000);
    assertEquals(address.getHost(), "127.0.0.1");
    assertEquals(address.getPort(), 8000);
  }
}
