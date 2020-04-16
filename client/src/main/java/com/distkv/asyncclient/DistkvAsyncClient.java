package com.distkv.asyncclient;


import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse;
import java.util.concurrent.CompletableFuture;

/**
 * The DistkvAsyncClient class is used to connect to the distkv server
 * and do operations asynchronously.
 */
public interface DistkvAsyncClient {

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

  String getActivedNamespace();

  /**
   * Drop the k-v pair for a given key.
   *
   * @param key The key to be dropped.
   */
  CompletableFuture<DistkvResponse> drop(String key);

  /**
   * Expire a key.
   *
   * @param key The key to be expired.
   * @param expireTime Millisecond level to set expire.
   */
  CompletableFuture<DistkvResponse> expire(String key, long expireTime);

  /**
   * Query if a key exists.
   *
   * @param key The key if exists.
   */
  CompletableFuture<DistkvResponse> exists(String key);

  /**
   * Get the time to live by the given key. The time unit is millisecond.
   *
   * @param key The key to be query TTL.
   */
  CompletableFuture<DistkvResponse> ttl(String key);


  /**
   * Get the distkv string proxy.
   *
   * @return The distkv string proxy.
   */
  DistkvAsyncStringProxy strs();

  /**
   * Get the distkv list proxy.
   *
   * @return The distkv list proxy.
   */
  DistkvAsyncListProxy lists();

  /**
   * Get the distkv set proxy.
   *
   * @return The distkv set proxy.
   */
  DistkvAsyncSetProxy sets();

  /**
   * Get the distkv dict proxy.
   *
   * @return The distkv string proxy.
   */
  DistkvAsyncDictProxy dicts();

  /**
   * Get the distkv sortedList proxy.
   *
   * @return The distkv sortedList proxy.
   */
  DistkvAsyncSlistProxy slists();

  /**
   * Get the distkv int proxy
   *
   * @return The distkv int proxy
   */
  DistkvAsyncIntProxy ints();

}
