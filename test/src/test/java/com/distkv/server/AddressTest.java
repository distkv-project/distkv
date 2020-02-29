package com.distkv.server;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AddressTest {

  public static final String DEFAULT_HOST = "127.0.1.1";

  @Test
  public void testDefaultAddress() {
    Address address = Address.defaultAddress();
    assertEquals(address.getHost(), DEFAULT_HOST);
    assertEquals(address.getPort(), 8082);
  }

  @Test
  public void  testFrom() {
    Address address = Address.from(8081);
    assertEquals(address.getHost(), DEFAULT_HOST);
    assertEquals(address.getPort(), 8081);

    address = Address.from("127.0.0.1");
    assertEquals(address.getHost(), "127.0.0.1");
    assertEquals(address.getPort(), 8082);

    address = Address.from("127.0.0.1", 8000);
    assertEquals(address.getHost(), "127.0.0.1");
    assertEquals(address.getPort(), 8000);
  }
}
