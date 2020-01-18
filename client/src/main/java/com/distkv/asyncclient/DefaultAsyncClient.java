package com.distkv.asyncclient;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.common.exception.DistKVException;
import com.distkv.rpc.service.DistKVDictService;
import com.distkv.rpc.service.DistKVListService;
import com.distkv.rpc.service.DistKVSetService;
import com.distkv.rpc.service.DistKVSortedListService;
import com.distkv.rpc.service.DistKVStringService;

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
    Proxy<DistKVStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DistKVStringService.class);
    stringProxy = new DstAsyncStringProxy(strRpcProxy.getService(rpcClient));

    // Setup list proxy.
    Proxy<DistKVListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DistKVListService.class);
    listProxy = new DstAsyncListProxy(listRpcProxy.getService(rpcClient));

    // Setup set proxy.
    Proxy<DistKVSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DistKVSetService.class);
    setProxy = new DstAsyncSetProxy(setRpcProxy.getService(rpcClient));

    // Setup dict proxy.
    Proxy<DistKVDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DistKVDictService.class);
    dictProxy = new DstAsyncDictProxy(dictRpcProxy.getService(rpcClient));

    // Setup sortedList proxy.
    Proxy<DistKVSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DistKVSortedListService.class);
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
