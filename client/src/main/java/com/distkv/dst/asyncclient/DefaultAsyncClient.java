package com.distkv.dst.asyncclient;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
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

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
            .address(serverAddress)
            .build();

    // Setup string proxy.
    Client strRpcClient = new NettyClient(clientConfig);
    strRpcClient.open();
    Proxy<DstStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DstStringService.class);
    stringProxy = new DstAsyncStringProxy(strRpcProxy.getService(strRpcClient));

    // Setup list proxy.
    Client listRpcClient = new NettyClient(clientConfig);
    listRpcClient.open();
    Proxy<DstListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DstListService.class);
    listProxy = new DstAsyncListProxy(listRpcProxy.getService(listRpcClient));

    // Setup set proxy.
    Client setRpcClient = new NettyClient(clientConfig);
    setRpcClient.open();
    Proxy<DstSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setProxy = new DstAsyncSetProxy(setRpcProxy.getService(setRpcClient));

    // Setup dict proxy.
    Client dictRpcClient = new NettyClient(clientConfig);
    dictRpcClient.open();
    Proxy<DstDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictProxy = new DstAsyncDictProxy(dictRpcProxy.getService(dictRpcClient));

    // Setup sortedList proxy.
    Client sortedListClient = new NettyClient(clientConfig);
    sortedListClient.open();
    Proxy<DstSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListProxy = new DstAsyncSortedListProxy(sortedListRpcProxy.getService(sortedListClient));
  }

  @Override
  public boolean connect() {
    return true;
  }

  @Override
  public boolean isConnect() {
    return true;
  }

  @Override
  public boolean disConnect() {
    return true;
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
