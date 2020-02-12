package com.distkv.asyncclient;

import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.service.DistkvService;

public class DefaultAsyncClient implements DistkvAsyncClient {

  private DistkvAsyncStringProxy stringProxy;
  private DistkvAsyncListProxy listProxy;
  private DistkvAsyncSetProxy setProxy;
  private DistkvAsyncDictProxy dictProxy;
  private DistkvAsyncSortedListProxy sortedListProxy;
  private Client rpcClient;

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
            .address(serverAddress)
            .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);

    stringProxy = new DistkvAsyncStringProxy(distkvRpcProxy.getService(rpcClient));
    listProxy = new DistkvAsyncListProxy(distkvRpcProxy.getService(rpcClient));
    setProxy = new DistkvAsyncSetProxy(distkvRpcProxy.getService(rpcClient));
    dictProxy = new DistkvAsyncDictProxy(distkvRpcProxy.getService(rpcClient));
    sortedListProxy = new DistkvAsyncSortedListProxy(distkvRpcProxy.getService(rpcClient));
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
  public DistkvAsyncStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DistkvAsyncListProxy lists() {
    return listProxy;
  }

  @Override
  public DistkvAsyncSetProxy sets() {
    return setProxy;
  }

  @Override
  public DistkvAsyncDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DistkvAsyncSortedListProxy sortedLists() {
    return sortedListProxy;
  }

}
