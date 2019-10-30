package org.dst.rpc.transport.netty;

import org.dst.rpc.core.common.URL;
import org.dst.rpc.transport.api.Handler;
import org.dst.rpc.transport.api.Server;
import org.dst.rpc.transport.api.ServerFactory;

/**
 * NettyFactory实现
 */
public class NettyTransportFactory extends ServerFactory {

  private NettyTransportFactory() {
  }

  private static class InstanceHolder {

    private static ServerFactory factory = new NettyTransportFactory();
  }

  public static ServerFactory getInstance() {
    return InstanceHolder.factory;
  }

  @Override
  protected Server doCreateServer(URL url, Handler handler) {
    return new NettyServer(url, handler);
  }
}
