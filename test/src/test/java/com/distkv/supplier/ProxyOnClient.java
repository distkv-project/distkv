package com.distkv.supplier;

import com.distkv.common.exception.DistKVException;
import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;

public class ProxyOnClient<T> implements AutoCloseable {

  private Client client = null;
  private Proxy<T> proxy;

  public ProxyOnClient(Class<T> clazz, int serverPort) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(String.format("dst://127.0.0.1:%d", serverPort))
        .build();
    int k = 10;
    while (k > 0) {
      try {
        client = new NettyClient(clientConfig);
        client.open();
        proxy = new Proxy<>();
        proxy.setInterfaceClass(clazz);
      } catch (Exception e) {
        try {
          Thread.sleep(200);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        k--;
        continue;
      }
      break;
    }
    if (k <= 0) {
      throw new DistKVException("connect error");
    }
  }

  public T getService() {
    return proxy.getService(client);
  }

  private void closeConnection() {
    // TODO(qwang): What should I do here?
  }

  @Override
  public void close() {
    this.closeConnection();
  }
}
