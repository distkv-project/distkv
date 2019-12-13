package com.distkv.dst.asyncclient;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstStringService;

public class DefaultAsyncClient implements DstAsyncClient {

  private DstAsyncStringProxy stringProxy;
  private DstAsyncListProxy listProxy;
  private DstAsyncSetProxy setProxy;
  private DstAsyncDictProxy dictProxy;
  private DstAsyncSortedListProxy sortedListProxy;
  private Client strRpcClient;
  private Client listRpcClient;
  private Client setRpcClient;
  private Client dictRpcClient;
  private Client sortedListRpcClient;

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
            .address(serverAddress)
            .build();

    // Setup string proxy.
    strRpcClient = new NettyClient(clientConfig);
    strRpcClient.open();
    Proxy<DstStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DstStringService.class);
    stringProxy = new DstAsyncStringProxy(strRpcProxy.getService(strRpcClient));

    // Setup list proxy.
    listRpcClient = new NettyClient(clientConfig);
    listRpcClient.open();
    Proxy<DstListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DstListService.class);
    listProxy = new DstAsyncListProxy(listRpcProxy.getService(listRpcClient));

    // Setup set proxy.
    setRpcClient = new NettyClient(clientConfig);
    setRpcClient.open();
    Proxy<DstSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setProxy = new DstAsyncSetProxy(setRpcProxy.getService(setRpcClient));

    // Setup dict proxy.
    dictRpcClient = new NettyClient(clientConfig);
    dictRpcClient.open();
    Proxy<DstDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictProxy = new DstAsyncDictProxy(dictRpcProxy.getService(dictRpcClient));

    // Setup sortedList proxy.
    sortedListRpcClient = new NettyClient(clientConfig);
    sortedListRpcClient.open();
    Proxy<DstSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListProxy = new DstAsyncSortedListProxy(
            sortedListRpcProxy.getService(sortedListRpcClient));
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
      strRpcClient.close();
      listRpcClient.close();
      setRpcClient.close();
      dictRpcClient.close();
      sortedListRpcClient.close();
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
