package test.org.dst.supplier;

import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.protocol.Options;

public class ProxyOnClient<T> implements AutoCloseable {

  /**
   * The url of the rpc server address.
   */
  private  static final String SERVER_URL = "list://127.0.0.1:8082";

  /**
   * The client that connects to the rpc server.
   */
  private RpcClient client;

  /**
   * The type of the service proxy.
   */
  private Class<T> proxyClass;

  private T service;

  public ProxyOnClient(Class<T> proxyClass) {
    this.proxyClass = proxyClass;
    this.openConnection();
  }

  private void openConnection() {
    RpcClientOptions options = new RpcClientOptions();
    options.setProtocolType(Options.ProtocolType.PROTOCOL_BAIDU_STD_VALUE);
    options.setWriteTimeoutMillis(1000);
    options.setReadTimeoutMillis(1000);
    options.setMaxTotalConnections(1000);
    options.setMinIdleConnections(10);
    client = new RpcClient(SERVER_URL, options);
    service = BrpcProxy.getProxy(client, proxyClass);
  }

  public T getService() {
    return service;
  }

  private void closeConnection() {
    client.stop();
  }

  @Override
  public void close() {
    this.closeConnection();
  }
}
