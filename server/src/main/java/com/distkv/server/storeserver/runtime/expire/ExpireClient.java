package com.distkv.server.storeserver.runtime.expire;

public interface ExpireClient {

  /**
   * Connect to distkv server.
   *
   * @return True if succeeded to connect, otherwise it's false.
   */
  void connect();

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

  void strDrop(String key);

  void listDrop(String key);

  void setDrop(String key);

  void dictDrop(String key);

  void slistDrop(String key);

  void intDrop(String key);
}
