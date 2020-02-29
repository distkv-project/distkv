package com.distkv.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Node Address. Only Support IPV4 now.
 */
public class Address {

  private static final Logger LOGGER = LoggerFactory.getLogger(Address.class);
  private static final int DEFAULT_PORT = 8082;

  private String host;
  private int port;

  /**
   * build address with local host and default port
   * @return address with specified host and default port.
   */
  public static Address defaultAddress() {
    return from(DEFAULT_PORT);
  }

  /**
   * build address with specified host and default port
   * @param host specified host.
   * @return address with specified host and default port.
   */
  public static Address from(String host) {
    return from(host, DEFAULT_PORT);
  }

  /**
   * build address with local host and specified port.
   * @param port specified port
   * @return address with local host and specified port.
   */
  public static Address from(int port) {
    try {
      InetAddress address = InetAddress.getLocalHost();
      return from(address.getHostAddress(), port);
    } catch (UnknownHostException e) {
      // deal with exception
      throw new RuntimeException(e);
    }
  }

  /**
   * build address with specified host and specified port.
   * @param host specified host
   * @param port specified port
   * @return address with specified host and specified port.
   */
  public static Address from(String host, int port) {
    Address address = new Address();
    address.setHost(host);
    address.setPort(port);
    return address;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
