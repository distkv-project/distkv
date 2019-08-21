package org.dst.client;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;
import org.dst.server.service.DstStringService;

public class DefaultDstClient implements DstClient {

  private RpcClient rcpClient;

  private DstStringService stringProxy;

  public DefaultDstClient(String serverAddress) {
    RpcClientOptions clientOptions = new RpcClientOptions();
    clientOptions.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    clientOptions.setWriteTimeoutMillis(1000);
    clientOptions.setReadTimeoutMillis(1000);
    clientOptions.setMaxTotalConnections(1000);
    clientOptions.setMinIdleConnections(10);
    rcpClient = new RpcClient(serverAddress, clientOptions);

    stringProxy = BrpcProxy.getProxy(rcpClient, DstStringService.class);
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
    return new DstStringProxy(stringProxy);
  }
}
