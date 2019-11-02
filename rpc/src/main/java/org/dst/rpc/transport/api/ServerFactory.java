package org.dst.rpc.transport.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.exception.TransportException;
import org.dst.rpc.core.model.IpPortPair;

/**
 * 必须是单例的
 */
public abstract class ServerFactory {

  private Map<IpPortPair, Server> activeServer = new ConcurrentHashMap<>();

  /**
   * 创建一个新的Server，如果server对应的ip&port已经存在，直接从缓存中拿取可用的Server。
   */
  public Server createServer(URL url, Handler handler) {
    IpPortPair serverAddress = url.getIpPortPair();
    Server server;
    if (activeServer.containsKey(serverAddress)) {
      server = activeServer.get(serverAddress);
      if (server.isOpen()) {
        RoutableHandler routableHandler = server.getRoutableHandler();
        if (routableHandler == null) {
          throw new TransportException("Server's routableHandler can't be null");
        }
        if (handler instanceof RoutableHandler) {
          routableHandler.merge((RoutableHandler) handler);
        } else {
          routableHandler.registerHandler(handler);
        }
        return server;
      } else {
        // 服务已经关闭了，从map中移除，然后新建一个Server存进map
        activeServer.remove(serverAddress);
      }
    }

    // 在URL请求的ip&port的地址上面没有服务，则创建一个服务
    server = doCreateServer(url, handler);
    // 这里不要open server，工厂除了创建一个新的Server以外不应该干涉Server的生命周期
    activeServer.put(serverAddress, server);
    return server;
  }

  protected abstract Server doCreateServer(URL url, Handler handler);

}
