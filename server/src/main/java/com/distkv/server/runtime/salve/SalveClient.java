package com.distkv.server.runtime.salve;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.rpc.service.DistKVDictService;
import com.distkv.rpc.service.DistKVListService;
import com.distkv.rpc.service.DistKVSetService;
import com.distkv.rpc.service.DistKVSortedListService;
import com.distkv.rpc.service.DistKVStringService;

public class SalveClient {

  private DistKVStringService stringService;

  private DistKVListService listService;

  private DistKVSetService setService;

  private DistKVDictService dictService;

  private DistKVSortedListService sortedListService;

  private Client rpcClient;

  private boolean isOpen;

  public SalveClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(serverAddress)
        .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();
    isOpen = true;
    // Setup str proxy.
    Proxy<DistKVStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DistKVStringService.class);
    stringService = strRpcProxy.getService(rpcClient);

    // Setup list proxy.
    Proxy<DistKVListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DistKVListService.class);
    listService = listRpcProxy.getService(rpcClient);

    // Setup set proxy.
    Proxy<DistKVSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DistKVSetService.class);
    setService = setRpcProxy.getService(rpcClient);

    // Setup dict proxy.
    Proxy<DistKVDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DistKVDictService.class);
    dictService = dictRpcProxy.getService(rpcClient);

    // Setup sortedList proxy.
    Proxy<DistKVSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DistKVSortedListService.class);
    sortedListService = sortedListRpcProxy.getService(rpcClient);

  }

  public void closeClient() {
    isOpen = false;
    rpcClient.close();
  }

  public boolean isOpen() {
    return isOpen;
  }

  public DistKVStringService getStringService() {
    if (isOpen) {
      return stringService;
    } else {
      return null;
    }
  }

  public DistKVListService getListService() {
    if (isOpen) {
      return listService;
    } else {
      return null;
    }
  }

  public DistKVSetService getSetService() {
    return setService;
  }

  public DistKVDictService getDictService() {
    if (isOpen) {
      return dictService;
    } else {
      return null;
    }

  }

  public DistKVSortedListService getSortedListService() {
    if (isOpen) {
      return sortedListService;
    } else {
      return null;
    }
  }
}
