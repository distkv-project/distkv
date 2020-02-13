package com.distkv.client;

import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.service.DistkvService;

public class DefaultDistkvClient implements DistkvClient {

  // TODO(qwang): Rename to StringInterface or StringQueryer.
  private DistkvStringProxy stringProxy;

  private DistkvListProxy listProxy;

  private DistkvSetProxy setProxy;

  private DistkvDictProxy dictProxy;

  private DistkvSortedListProxy sortedListProxy;

  private Client rpcClient;

  public DefaultDistkvClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
          .address(serverAddress)
          .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();

    // Setup list proxy.
    // TODO(qwang): Refine this to rpcClient.getService<DstStringService>();
    Proxy<DistkvService> distkvRpcProxy = new Proxy<>();
    distkvRpcProxy.setInterfaceClass(DistkvService.class);

    stringProxy = new DistkvStringProxy(distkvRpcProxy.getService(rpcClient));
    listProxy = new DistkvListProxy(distkvRpcProxy.getService(rpcClient));
    setProxy = new DistkvSetProxy(distkvRpcProxy.getService(rpcClient));
    dictProxy = new DistkvDictProxy(distkvRpcProxy.getService(rpcClient));
    sortedListProxy = new DistkvSortedListProxy(distkvRpcProxy.getService(rpcClient));
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
  public DistkvStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DistkvDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DistkvListProxy lists() {
    return listProxy;
  }

  @Override
  public DistkvSetProxy sets() {
    return setProxy;
  }

  @Override
  public DistkvSortedListProxy sortedLists() {
    return sortedListProxy;
  }
}
