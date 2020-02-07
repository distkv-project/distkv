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
import com.distkv.rpc.service.DistkvService;

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
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);

    stringProxy = new DstStringProxy(distkvRpcProxy.getService(rpcClient));
    listProxy = new DstListProxy(distkvRpcProxy.getService(rpcClient));
    setProxy = new DstSetProxy(distkvRpcProxy.getService(rpcClient));
    dictProxy = new DistKVDictProxy(distkvRpcProxy.getService(rpcClient));
    sortedListProxy = new DstSortedListProxy(distkvRpcProxy.getService(rpcClient));
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
