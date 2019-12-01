package com.distkv.dst.test.supplier;


import com.distkv.drpc.Reference;

public class ProxyOnClient<T> implements AutoCloseable {

  private Reference<T> realProxy = new Reference<>();

  public ProxyOnClient(Class<T> proxyClass, int serverPort) {
    realProxy.setAddress(String.format("dst://127.0.0.1:%d", serverPort));
    realProxy.setInterfaceClass(proxyClass);
  }


  public T getService() {
    return realProxy.getReference();
  }

  private void closeConnection() {
    // TODO(qwang): What should I do here?
  }

  @Override
  public void close() {
    this.closeConnection();
  }
}
