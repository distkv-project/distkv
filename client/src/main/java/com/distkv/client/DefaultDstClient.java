package com.distkv.dst.client;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.rpc.service.DstStringService;

public class DefaultDstClient implements DstClient {

  // TODO(qwang): Rename to StringInterface or StringQueryer.
  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;

  private DstDictProxy dictProxy;

  private DstSortedListProxy sortedListProxy;

  private Client rpcClient;

  public DefaultDstClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
          .address(serverAddress)
          .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();

    // Setup list proxy.
    // TODO(qwang): Refine this to rpcClient.getService<DstStringService>();
    Proxy<DstStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DstStringService.class);
    stringProxy = new DstStringProxy(strRpcProxy.getService(rpcClient));

    // Setup list proxy.
    Proxy<DstListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DstListService.class);
    listProxy = new DstListProxy(listRpcProxy.getService(rpcClient));

    // Setup set proxy.
    Proxy<DstSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setProxy = new DstSetProxy(setRpcProxy.getService(rpcClient));

    // Setup dict proxy.
    Proxy<DstDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictProxy = new DstDictProxy(dictRpcProxy.getService(rpcClient));

    // Setup sortedList proxy.
    Proxy<DstSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListProxy = new DstSortedListProxy(sortedListRpcProxy.getService(rpcClient));
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
