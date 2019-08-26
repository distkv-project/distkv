package org.dst.client;

/**
 * The DstClient class is used to connect to the dst server.
 */
public interface DstClient {

  /**
   * Connect to dst server.
   *
   * @return True if succeeded to connect, otherwise it's false.
   */
  boolean connect();

  /**
   * Whether the client connected to dst server.
   *
   * @return True if it connected, otherwise it's false.
   */
  boolean isConnected();

  /**
   * Disconnect to dst server.
   */
  boolean disconnect();


  /**
   * Get the dst string proxy.
   *
   * @return The dst string proxy.
   */
  DstStringProxy strs();


  /**
   * Get the dst dict proxy.
   *
   * @return The dst string proxy.
   */
  DstDictProxy dicts();

  /**
   * Get the dst list proxy.
   *
   * @return The dst list proxy.
   */
  DstListProxy lists();

  /**
   * Get the dst set proxy.
   *
   * @return The dst set proxy.
   */
  DstSetProxy sets();

}
