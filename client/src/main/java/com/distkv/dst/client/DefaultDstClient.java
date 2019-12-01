package com.distkv.dst.client;

import com.distkv.drpc.Reference;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.rpc.service.DstListService;
import com.distkv.dst.rpc.service.DstSetService;
import com.distkv.dst.rpc.service.DstSortedListService;
import com.distkv.dst.rpc.service.DstStringService;

public class DefaultDstClient implements DstClient {

  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;

  private DstDictProxy dictProxy;

  private DstSortedListProxy sortedListProxy;


  public DefaultDstClient(String serverAddress) {
    // Setup string proxy.
    Reference<DstStringService> stringRpcProxy = new Reference<>();
    stringRpcProxy.setAddress(serverAddress);
    stringRpcProxy.setInterfaceClass(DstStringService.class);
    stringProxy = new DstStringProxy(stringRpcProxy.getReference());

    // Setup list proxy.
    Reference<DstListService> listRpcProxy = new Reference<>();
    listRpcProxy.setAddress(serverAddress);
    listRpcProxy.setInterfaceClass(DstListService.class);
    listProxy = new DstListProxy(listRpcProxy.getReference());

    // Setup set proxy.
    Reference<DstSetService> setRpcProxy = new Reference<>();
    setRpcProxy.setAddress(serverAddress);
    setRpcProxy.setInterfaceClass(DstSetService.class);
    setProxy = new DstSetProxy(setRpcProxy.getReference());

    // Setup dict proxy.
    Reference<DstDictService> dictRpcProxy = new Reference<>();
    dictRpcProxy.setAddress(serverAddress);
    dictRpcProxy.setInterfaceClass(DstDictService.class);
    dictProxy = new DstDictProxy(dictRpcProxy.getReference());

    // Setup sortedList proxy.
    Reference<DstSortedListService> sortedListRpcProxy = new Reference<>();
    sortedListRpcProxy.setAddress(serverAddress);
    sortedListRpcProxy.setInterfaceClass(DstSortedListService.class);
    sortedListProxy = new DstSortedListProxy(sortedListRpcProxy.getReference());
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
    // TODO(qwang): What should we do here?
    return true;
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
