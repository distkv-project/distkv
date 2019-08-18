package org.dst.client;

public interface DstClient {

  boolean connect();

  boolean isConnected();

  boolean disconnect();

  DstStringProxy str();
}
