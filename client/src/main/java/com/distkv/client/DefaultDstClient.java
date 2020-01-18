package com.distkv.client;

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

public class DefaultDstClient implements DstClient {

  // TODO(qwang): Rename to StringInterface or StringQueryer.
  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;

  private DistKVDictProxy dictProxy;

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
    Proxy<DistKVStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DistKVStringService.class);
    stringProxy = new DstStringProxy(strRpcProxy.getService(rpcClient));

    // Setup list proxy.
    Proxy<DistKVListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DistKVListService.class);
    listProxy = new DstListProxy(listRpcProxy.getService(rpcClient));

    // Setup set proxy.
    Proxy<DistKVSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DistKVSetService.class);
    setProxy = new DstSetProxy(setRpcProxy.getService(rpcClient));

    // Setup dict proxy.
    Proxy<DistKVDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DistKVDictService.class);
    dictProxy = new DistKVDictProxy(dictRpcProxy.getService(rpcClient));

    // Setup sortedList proxy.
    Proxy<DistKVSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DistKVSortedListService.class);
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
    } catch (DistKVException ex) {
      throw new DistKVException(String.format("Failed close the clients : %s", ex.getMessage()));
    }
  }

  @Override
  public DstStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DistKVDictProxy dicts() {
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
