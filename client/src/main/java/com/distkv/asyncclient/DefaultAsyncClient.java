package com.distkv.asyncclient;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.common.exception.DistKVException;
import com.distkv.rpc.service.DistkvService;

public class DefaultAsyncClient implements DstAsyncClient {

  private DstAsyncStringProxy stringProxy;
  private DstAsyncListProxy listProxy;
  private DstAsyncSetProxy setProxy;
  private DstAsyncDictProxy dictProxy;
  private DstAsyncSortedListProxy sortedListProxy;
  private Client rpcClient;

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
            .address(serverAddress)
            .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);

    stringProxy = new DstAsyncStringProxy(distkvRpcProxy.getService(rpcClient));
    listProxy = new DstAsyncListProxy(distkvRpcProxy.getService(rpcClient));
    setProxy = new DstAsyncSetProxy(distkvRpcProxy.getService(rpcClient));
    dictProxy = new DstAsyncDictProxy(distkvRpcProxy.getService(rpcClient));
    sortedListProxy = new DstAsyncSortedListProxy(distkvRpcProxy.getService(rpcClient));
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
    } catch (DistKVException ex) {
      throw new DistKVException(String.format("Failed close the clients : %s", ex.getMessage()));
    }
  }

  @Override
  public DstAsyncStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DstAsyncListProxy lists() {
    return listProxy;
  }

  @Override
  public DstAsyncSetProxy sets() {
    return setProxy;
  }

  @Override
  public DstAsyncDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DstAsyncSortedListProxy sortedLists() {
    return sortedListProxy;
  }

}
