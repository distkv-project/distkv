package com.distkv.asyncclient;

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

public class DefaultAsyncClient implements DistkvAsyncClient {

  private DistkvAsyncStringProxy stringProxy;
  private DistkvAsyncListProxy listProxy;
  private DistkvAsyncSetProxy setProxy;
  private DistkvAsyncDictProxy dictProxy;
  private DistkvAsyncSortedListProxy sortedListProxy;
  private Client rpcClient;

  public DefaultAsyncClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
            .address(serverAddress)
            .build();

    rpcClient = new NettyClient(clientConfig);
    // Setup string proxy.
    rpcClient.open();
    Proxy<DistkvStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DistkvStringService.class);
    stringProxy = new DistkvAsyncStringProxy(strRpcProxy.getService(rpcClient));

    // Setup list proxy.
    Proxy<DistkvListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DistkvListService.class);
    listProxy = new DistkvAsyncListProxy(listRpcProxy.getService(rpcClient));

    // Setup set proxy.
    Proxy<DistkvSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DistkvSetService.class);
    setProxy = new DistkvAsyncSetProxy(setRpcProxy.getService(rpcClient));

    // Setup dict proxy.
    Proxy<DistkvDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DistkvDictService.class);
    dictProxy = new DistkvAsyncDictProxy(dictRpcProxy.getService(rpcClient));

    // Setup sortedList proxy.
    Proxy<DistkvSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DistkvSortedListService.class);
    sortedListProxy = new DistkvAsyncSortedListProxy(
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
    } catch (DistkvException ex) {
      throw new DistkvException(String.format("Failed close the clients : %s", ex.getMessage()));
    }
  }

  @Override
  public DistkvAsyncStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DistkvAsyncListProxy lists() {
    return listProxy;
  }

  @Override
  public DistkvAsyncSetProxy sets() {
    return setProxy;
  }

  @Override
  public DistkvAsyncDictProxy dicts() {
    return dictProxy;
  }

  @Override
  public DistkvAsyncSortedListProxy sortedLists() {
    return sortedListProxy;
  }

}
