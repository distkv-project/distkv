package com.distkv.dst.server.runtime.salver;

import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.rpc.service.DstStringService;

public class SalverClient {

  private DstStringService stringService;

  private DstListService listService;

  private DstSetService setService;

  private DstDictService dictService;

  private DstSortedListService sortedListService;

  private Client rpcClient;

  public SalverClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(serverAddress)
        .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();

    // Setup str proxy.
    // TODO(qwang): Refine this to rpcClient.getService<DstStringService>();
    Proxy<DstStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DstStringService.class);
    stringService = strRpcProxy.getService(rpcClient);

    // Setup list proxy.
    Proxy<DstListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DstListService.class);
    listService = listRpcProxy.getService(rpcClient);

    // Setup set proxy.
    Proxy<DstSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setService = setRpcProxy.getService(rpcClient);

    // Setup dict proxy.
    Proxy<DstDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictService = dictRpcProxy.getService(rpcClient);

    // Setup sortedList proxy.
    Proxy<DstSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListService = sortedListRpcProxy.getService(rpcClient);
  }

  public DstStringService getStringService() {
    return stringService;
  }

  public DstListService getListService() {
    return listService;
  }

  public DstSetService getSetService() {
    return setService;
  }

  public DstDictService getDictService() {
    return dictService;
  }

  public DstSortedListService getSortedListService() {
    return sortedListService;
  }
}
