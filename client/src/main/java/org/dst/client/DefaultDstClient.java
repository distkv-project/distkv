package org.dst.client;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.server.service.DstListService;
import org.dst.server.service.DstSetService;
import org.dst.server.service.DstStringService;

public class DefaultDstClient implements DstClient {

  private RpcClient stringClient;

  private RpcClient setClient;

  private RpcClient listClient;

  private DstStringProxy stringProxy;

  private DstListProxy listProxy;

  private DstSetProxy setProxy;



  public DefaultDstClient(String serverAddress) {
    RpcClientOptions clientOptions = new RpcClientOptions();
    clientOptions.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    clientOptions.setWriteTimeoutMillis(1000);
    clientOptions.setReadTimeoutMillis(1000);
    clientOptions.setMaxTotalConnections(1000);
    clientOptions.setMinIdleConnections(10);


    DstStringService stringService = BrpcProxy.getProxy(stringClient, DstStringService.class);
    DstListService listService = BrpcProxy.getProxy(listClient, DstListService.class);
    DstSetService setService = BrpcProxy.getProxy(setClient, DstSetService.class);

    stringClient = new RpcClient(serverAddress, clientOptions);
    setClient = new RpcClient(serverAddress, clientOptions);
    listClient = new RpcClient(serverAddress, clientOptions);

    stringProxy = new DstStringProxy(stringService);
    listProxy = new DstListProxy(listService);
    setProxy = new DstSetProxy(setService);

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
    return true;
  }

  @Override
  public DstStringProxy strs() {
    return stringProxy;
  }

  @Override
  public DstListProxy lists() {
    return listProxy;
  }

  @Override
  public DstSetProxy sets() {
    return setProxy;
  }

}
