package org.dst.rpc.transport.api;

import java.util.List;
import org.dst.rpc.core.common.URL;

/**
 * 一个可路由的RoutableHandler，当在一个端口暴露不止一个服务的时候，需要根据请求对不同的服务名称进行路由。 4
 */
public interface RoutableHandler extends Handler {

  Handler getHandlerByServerName(URL url);

  void registerHandler(Handler handler);

  /**
   * 和另一个RoutableHandler合并，会首先列出合并对象的所有子handler，如果子handler中含有RoutableHandler，会递归合并
   */
  void merge(RoutableHandler handler);

  List<Handler> getAllHandler();

  @Override
  Object handle(Object message);

  @Override
  default String getServerName() {
    throw new UnsupportedOperationException();
  }

}
