package com.distkv.asyncclient;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.common.exception.DstException;
import com.distkv.rpc.service.DstDictService;
import com.distkv.rpc.service.DstListService;
import com.distkv.rpc.service.DstSetService;
import com.distkv.rpc.service.DstSortedListService;
import com.distkv.rpc.service.DstStringService;

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
    // Setup string proxy.
    rpcClient.open();
    Proxy<DstStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DstStringService.class);
    stringProxy = new DstAsyncStringProxy(strRpcProxy.getService(rpcClient));

    // Setup list proxy.
    Proxy<DstListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DstListService.class);
    listProxy = new DstAsyncListProxy(listRpcProxy.getService(rpcClient));

    // Setup set proxy.
    Proxy<DstSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setProxy = new DstAsyncSetProxy(setRpcProxy.getService(rpcClient));

    // Setup dict proxy.
    Proxy<DstDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictProxy = new DstAsyncDictProxy(dictRpcProxy.getService(rpcClient));

    // Setup sortedList proxy.
    Proxy<DstSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListProxy = new DstAsyncSortedListProxy(
            sortedListRpcProxy.getService(rpcClient));
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
    } catch (DstException ex) {
      throw new DstException(String.format("Failed close the clients : %s", ex.getMessage()));
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
