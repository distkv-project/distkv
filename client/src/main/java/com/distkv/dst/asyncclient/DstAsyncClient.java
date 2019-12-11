package com.distkv.dst.asyncclient;

/**
 * The DstAsyncClient class is used to connect to the dst server with async way.
 */
public interface DstAsyncClient {

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
  boolean isConnect();

  /**
   * Disconnect to dst server.
   */
  boolean disConnect();

  /**
   * Get the dst string proxy.
   *
   * @return The dst string proxy.
   */
  DstAsyncStringProxy strs();

  /**
   * Get the dst list proxy.
   *
   * @return The dst list proxy.
   */
  DstAsyncListProxy lists();

  /**
   * Get the dst set proxy.
   *
   * @return The dst set proxy.
   */
  DstAsyncSetProxy sets();

  /**
   * Get the dst dict proxy.
   *
   * @return The dst string proxy.
   */
  DstAsyncDictProxy dicts();

  /**
   * Get the dst sortedList proxy.
   *
   * @return The dst sortedList proxy.
   */
  DstAsyncSortedListProxy sortedLists();
}
