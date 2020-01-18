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

  public SalveClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(serverAddress)
        .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();

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

  public DistKVStringService getStringService() {
    return stringService;
  }

  public DistKVListService getListService() {
    return listService;
  }

  public DistKVSetService getSetService() {
    return setService;
  }

  public DistKVDictService getDictService() {
    return dictService;
  }

  public DistKVSortedListService getSortedListService() {
    return sortedListService;
  }
}
