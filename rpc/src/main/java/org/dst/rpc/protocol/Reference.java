package org.dst.rpc.protocol;

import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.config.ParamConstants;
import org.dst.rpc.core.exception.DstException;
import org.dst.rpc.protocol.proxy.ProxyFactory;
import org.dst.rpc.transport.api.Client;
import org.dst.rpc.transport.netty.NettyClient;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class Reference<T> {

  private Class<T> interfaceClass;
  private String address;
  private boolean isAsync;

  private URL serverUrl;

  public Reference() {
    this.serverUrl = new URL();
  }

  public void setInterfaceClass(Class<T> interfaceClass) {
    this.interfaceClass = interfaceClass;
    serverUrl.setPath(interfaceClass.getName());
  }

  public void setAddress(String address) {
    this.address = address;
    if (!address.contains("://")) {
      throw new DstException("Empty protocol");
    }
    String protocol = address.substring(0, address.indexOf("://"));
    serverUrl.setProtocol(protocol);
    serverUrl.setAddress(address.substring(address.indexOf("://") + "://".length()));
  }

  // fixme : 不要设置，自动根据返回类型自行判断
  public void setAsync(boolean async) {
    isAsync = async;
    serverUrl.setConfig(ParamConstants.isAsync, String.valueOf(async));
  }

  public T getReference() {

    Client client = new NettyClient(serverUrl);
    client.init();
    Invoker invoker = new DefaultInvoker(client);
    return new ProxyFactory<T>().getProxy(interfaceClass, serverUrl, invoker);
  }

}
