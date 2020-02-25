package com.distkv.client;

import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;

/**
 * The DstClient class is used to connect to the dst server.
 */
public interface DistkvClient {

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
   * Active a namespace for this client.
   */
  void activeNamespace(String namespace);

  /**
   * Deactivate the already achieved Namespace
   */
  void deactiveNamespace();

  /**
   * Get actived namespace
   * @return the actived namespace's name
   */
  String getActivedNamespace();

  /**
   * Get the dst string proxy.
   *
   * @return The dst string proxy.
   */
  DistkvStringProxy strs();


  /**
   * Get the dst dict proxy.
   *
   * @return The dst string proxy.
   */
  DistkvDictProxy dicts();

  /**
   * Get the dst list proxy.
   *
   * @return The dst list proxy.
   */
  DistkvListProxy lists();

  /**
   * Get the dst set proxy.
   *
   * @return The dst set proxy.
   */
  DistkvSetProxy sets();

  /**
   * Get the dst sortedList proxy.
   *
   * @return The dst sortedList proxy.
   */
  DistkvSortedListProxy sortedLists();

  /**
   * Get the distkv int proxy
   *
   * @return The distkv int proxy
   */

}
