package com.distkv.supplier;

import com.distkv.common.utils.RuntimeUtil;
import org.drpc.Proxy;
import org.drpc.api.Client;
import org.drpc.config.ClientConfig;
import org.drpc.netty.DrpcClient;

public class ProxyOnClient<T> implements AutoCloseable {

  private Client client = null;
  private Proxy<T> proxy;

  public ProxyOnClient(Class<T> clazz, int serverPort) {
    ClientConfig clientConfig = ClientConfig.builder()
        .address(String.format("distkv://127.0.0.1:%d", serverPort))
        .build();
    RuntimeUtil.waitForCondition(() -> {
      try {
        client = new DrpcClient(clientConfig);
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
    client.close();
  }

  @Override
  public void close() {
    this.closeConnection();
  }
}
