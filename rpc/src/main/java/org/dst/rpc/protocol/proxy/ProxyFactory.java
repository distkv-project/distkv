package org.dst.rpc.protocol.proxy;

import java.lang.reflect.Proxy;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.protocol.Invoker;


public class ProxyFactory<T> {

  @SuppressWarnings("unchecked")
  public T getProxy(Class<T> clazz, URL url, Invoker invoker) {
    return (T) Proxy.newProxyInstance(
        clazz.getClassLoader(), new Class[]{clazz}, new ProxyHandler(clazz, url, invoker));
  }

}
