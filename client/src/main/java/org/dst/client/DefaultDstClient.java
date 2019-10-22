package org.dst.client;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.rpc.service.DstDictService;
import org.dst.rpc.service.DstListService;
import org.dst.rpc.service.DstSetService;
import org.dst.rpc.service.DstSortedListService;
import org.dst.rpc.service.DstStringService;

public class DefaultDstClient implements DstClient {

  private RpcClient stringClient;

  private RpcClient listClient;

  private RpcClient setClient;

  private RpcClient dictClient;

  private RpcClient sortedListClient;

  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;

  private DstDictProxy dictProxy;

  private DstSortedListProxy sortedListProxy;


  public DefaultDstClient(String serverAddress) {
    RpcClientOptions clientOptions = new RpcClientOptions();
    clientOptions.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    clientOptions.setWriteTimeoutMillis(1000);
    clientOptions.setReadTimeoutMillis(1000);
    clientOptions.setMaxTotalConnections(1000);
    clientOptions.setMinIdleConnections(10);

    stringClient = new RpcClient(serverAddress, clientOptions);
    listClient = new RpcClient(serverAddress, clientOptions);
    setClient = new RpcClient(serverAddress, clientOptions);
    dictClient = new RpcClient(serverAddress, clientOptions);
    sortedListClient = new RpcClient(serverAddress, clientOptions);

    DstStringService stringService = BrpcProxy.getProxy(stringClient, DstStringService.class);
    DstListService listService = BrpcProxy.getProxy(listClient, DstListService.class);
    DstSetService setService = BrpcProxy.getProxy(setClient, DstSetService.class);
    DstDictService dictService = BrpcProxy.getProxy(dictClient, DstDictService.class);
    DstSortedListService sortedListService =
        BrpcProxy.getProxy(sortedListClient, DstSortedListService.class);

    stringProxy = new DstStringProxy(stringService);
    listProxy = new DstListProxy(listService);
    setProxy = new DstSetProxy(setService);
    dictProxy = new DstDictProxy(dictService);
    sortedListProxy = new DstSortedListProxy(sortedListService);
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
    stringClient.stop();
    listClient.stop();
    setClient.stop();
    dictClient.stop();
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
