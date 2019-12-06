package com.distkv.dst.client;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.rpc.service.DstStringService;

public class DefaultDstClient implements DstClient {

  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;

  private DstDictProxy dictProxy;

  private DstSortedListProxy sortedListProxy;


  public DefaultDstClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
          .address(serverAddress)
          .build();

    // Setup string proxy.
    Client clientStr = new NettyClient(clientConfig);
    clientStr.open();
    Proxy<DstStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DstStringService.class);
    stringProxy = new DstStringProxy(strRpcProxy.getService(clientStr));

    // Setup list proxy.
    Client clientList = new NettyClient(clientConfig);
    clientList.open();
    Proxy<DstListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DstListService.class);
    listProxy = new DstListProxy(listRpcProxy.getService(clientList));

    // Setup set proxy.
    Client clientSet = new NettyClient(clientConfig);
    clientSet.open();
    Proxy<DstSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setProxy = new DstSetProxy(setRpcProxy.getService(clientSet));

    // Setup dict proxy.
    Client clientDict = new NettyClient(clientConfig);
    clientDict.open();
    Proxy<DstDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictProxy = new DstDictProxy(dictRpcProxy.getService(clientDict));

    // Setup sortedList proxy.
    Client clientSortedList = new NettyClient(clientConfig);
    clientSortedList.open();
    Proxy<DstSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListProxy = new DstSortedListProxy(sortedListRpcProxy.getService(clientSortedList));
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
    // TODO(qwang): What should we do here?
    return true;
  }

  @Override
  public DstStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DstDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DstListProxy lists() {
    return listProxy;
  }

  @Override
  public DstSetProxy sets() {
    return setProxy;
  }

  @Override
  public DstSortedListProxy sortedLists() {
    return sortedListProxy;
  }
}
