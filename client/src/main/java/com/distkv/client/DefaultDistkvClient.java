package com.distkv.client;

import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.service.DistkvDictService;
import com.distkv.rpc.service.DistkvListService;
import com.distkv.rpc.service.DistkvSetService;
import com.distkv.rpc.service.DistkvSortedListService;
import com.distkv.rpc.service.DistkvStringService;

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
    Proxy<DistkvStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DistkvStringService.class);
    stringProxy = new DistkvStringProxy(strRpcProxy.getService(rpcClient));

    // Setup list proxy.
    Proxy<DistkvListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DistkvListService.class);
    listProxy = new DistkvListProxy(listRpcProxy.getService(rpcClient));

    // Setup set proxy.
    Proxy<DistkvSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DistkvSetService.class);
    setProxy = new DistkvSetProxy(setRpcProxy.getService(rpcClient));

    // Setup dict proxy.
    Proxy<DistkvDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DistkvDictService.class);
    dictProxy = new DistkvDictProxy(dictRpcProxy.getService(rpcClient));

    // Setup sortedList proxy.
    Proxy<DistkvSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DistkvSortedListService.class);
    sortedListProxy = new DistkvSortedListProxy(sortedListRpcProxy.getService(rpcClient));
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
