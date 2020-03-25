package com.distkv.client;


/**
 * The DistkvClient class is used to connect to the distkv server.
 */
public interface DistkvClient {

  /**
   * Connect to distkv server.
   *
   * @return True if succeeded to connect, otherwise it's false.
   */
  boolean connect();

  /**
   * Whether the client connected to distkv server.
   *
   * @return True if it connected, otherwise it's false.
   */
  boolean isConnected();

  /**
   * Disconnect to distkv server.
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
   * Drop the k-v pair.
   *
   * @param key The key to be dropped.
   */
  void drop(String key);

  /**
   * Expire a key.
   *
   * @param key The key to be expired.
   * @param expireTime Millisecond level to set expire.
   */
  void expire(String key, long expireTime);

  /**
   * Get a servival time for a given key.
   *
   * @param key The key need to get the survival time.
   */
  void ttl(String key);

  /**
   * Get actived namespace
   * @return the actived namespace's name
   */
  String getActivedNamespace();

  /**
   * Get the distkv string proxy.
   *
   * @return The distkv string proxy.
   */
  DistkvStringProxy strs();


  /**
   * Get the distkv dict proxy.
   *
   * @return The distkv string proxy.
   */
  DistkvDictProxy dicts();

  /**
   * Get the distkv list proxy.
   *
   * @return The distkv list proxy.
   */
  DistkvListProxy lists();

  /**
   * Get the distkv set proxy.
   *
   * @return The distkv set proxy.
   */
  DistkvSetProxy sets();

  /**
   * Get the distkv sortedList proxy.
   *
   * @return The distkv sortedList proxy.
   */
  DistkvSortedListProxy sortedLists();


  /**
   * Get the distkv int proxy
   *
   * @return The distkv int proxy
   */
  DistkvIntProxy ints();

}
