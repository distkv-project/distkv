package com.distkv.client;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DistkvAsyncClient;
import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.service.DistkvService;

public class DefaultDistkvClient implements DistkvClient {

  private DistkvStringProxy stringProxy;

  private DistkvListProxy listProxy;

  private DistkvSetProxy setProxy;

  private DistkvDictProxy dictProxy;

  private DistkvSortedListProxy sortedListProxy;

  /// The DistkvSyncClient is wrapped with a DistkvAsyncClient.
  private DistkvAsyncClient asyncClient;

  public DefaultDistkvClient(String serverAddress) {
    asyncClient = new DefaultAsyncClient(serverAddress);

    stringProxy = new DistkvStringProxy(asyncClient.strs());
    listProxy = new DistkvListProxy(asyncClient.lists());
    setProxy = new DistkvSetProxy(asyncClient.sets());
    dictProxy = new DistkvDictProxy(asyncClient.dicts());
    sortedListProxy = new DistkvSortedListProxy(asyncClient.sortedLists());
  }

  @Override
  public boolean connect() {
    return true;
  }

  @Override
  public boolean isConnected() {
    return true;
  }

  @Override
  public boolean disconnect() {
    try {
      rpcClient.close();
      return true;
    } catch (DistkvException ex) {
      throw new DistkvException(String.format("Failed close the clients : %s", ex.getMessage()));
    }
  }

  @Override
  public DistkvStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DistkvDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DistkvListProxy lists() {
    return listProxy;
  }

  @Override
  public DistkvSetProxy sets() {
    return setProxy;
  }

  @Override
  public DistkvSortedListProxy sortedLists() {
    return sortedListProxy;
  }
}
