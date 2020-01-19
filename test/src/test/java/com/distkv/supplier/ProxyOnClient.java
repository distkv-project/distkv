package com.distkv.supplier;

import com.distkv.common.utils.RuntimeUtil;
import com.distkv.drpc.Proxy;
import com.distkv.drpc.api.Client;
import com.distkv.drpc.config.ClientConfig;
import com.distkv.drpc.netty.NettyClient;

public class ProxyOnClient<T> implements AutoCloseable {

  private Client client = null;
  private Proxy<T> proxy;

  public ProxyOnClient(Class<T> clazz, int serverPort) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(String.format("distkv://127.0.0.1:%d", serverPort))
        .build();
    RuntimeUtil.waitForCondition(() -> {
      try {
        client = new NettyClient(clientConfig);
        client.open();
        proxy = new Proxy<>();
        proxy.setInterfaceClass(clazz);
        return true;
      } catch (Exception e) {
        return false;
      }
    }, 60 * 1000 * 2);
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
