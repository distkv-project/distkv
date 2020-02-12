package com.distkv.server.storeserver.runtime.slave;

import org.dousi.Proxy;
import org.dousi.api.Client;
import org.dousi.config.ClientConfig;
import org.dousi.netty.NettyClient;
import com.distkv.rpc.service.DistkvDictService;
import com.distkv.rpc.service.DistkvListService;
import com.distkv.rpc.service.DistkvSetService;
import com.distkv.rpc.service.DistkvSortedListService;
import com.distkv.rpc.service.DistkvStringService;

public class SlaveClient {

  private DistkvStringService stringService;

  private DistkvListService listService;

  private DistkvSetService setService;

  private DistkvDictService dictService;

  private DistkvSortedListService sortedListService;

  private Client rpcClient;

  private boolean isOpen;

  public SlaveClient(String serverAddress) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(serverAddress)
        .build();

    rpcClient = new NettyClient(clientConfig);
    rpcClient.open();
    isOpen = true;
    // Setup str proxy.
    Proxy<DistkvStringService> strRpcProxy = new Proxy<>();
    strRpcProxy.setInterfaceClass(DistkvStringService.class);
    stringService = strRpcProxy.getService(rpcClient);

    // Setup list proxy.
    Proxy<DistkvListService> listRpcProxy = new Proxy<>();
    listRpcProxy.setInterfaceClass(DistkvListService.class);
    listService = listRpcProxy.getService(rpcClient);

    // Setup set proxy.
    Proxy<DistkvSetService> setRpcProxy = new Proxy<>();
    setRpcProxy.setInterfaceClass(DistkvSetService.class);
    setService = setRpcProxy.getService(rpcClient);

    // Setup dict proxy.
    Proxy<DistkvDictService> dictRpcProxy = new Proxy<>();
    dictRpcProxy.setInterfaceClass(DistkvDictService.class);
    dictService = dictRpcProxy.getService(rpcClient);

    // Setup sortedList proxy.
    Proxy<DistkvSortedListService> sortedListRpcProxy = new Proxy<>();
    sortedListRpcProxy.setInterfaceClass(DistkvSortedListService.class);
    sortedListService = sortedListRpcProxy.getService(rpcClient);

  }

  public void closeClient() {
    isOpen = false;
    rpcClient.close();
  }

  public boolean isOpen() {
    return isOpen;
  }

  public DistkvStringService getStringService() {
    if (isOpen) {
      return stringService;
    } else {
      return null;
    }
  }

  public DistkvListService getListService() {
    if (isOpen) {
      return listService;
    } else {
      return null;
    }
  }

  public DistkvSetService getSetService() {
    return setService;
  }

  public DistkvDictService getDictService() {
    if (isOpen) {
      return dictService;
    } else {
      return null;
    }

  }

  public DistkvSortedListService getSortedListService() {
    if (isOpen) {
      return sortedListService;
    } else {
      return null;
    }
  }
}
