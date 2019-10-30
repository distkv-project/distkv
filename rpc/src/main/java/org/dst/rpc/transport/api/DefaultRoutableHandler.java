package org.dst.rpc.transport.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.transport.api.async.Request;

/**
 *
 */
public class DefaultRoutableHandler implements RoutableHandler {

  private Map<String, Handler> handlerMap;

  @Override
  public List<Handler> getAllHandler() {
    return new ArrayList<>(handlerMap.values());
  }

  public DefaultRoutableHandler() {
    handlerMap = new ConcurrentHashMap<>();
  }

  @Override
  public Handler getHandlerByServerName(URL url) {

    return null;
  }

  @Override
  public void registerHandler(Handler handler) {
    String serverIdentify = handler.getServerName();
    handlerMap.put(serverIdentify, handler);
  }

  @Override
  public void merge(RoutableHandler handler) {
    List<Handler> handlers = handler.getAllHandler();
    if (handlers != null && handlers.size() > 0) {
      for (Handler h : handlers) {
        if (h instanceof RoutableHandler) {
          merge((RoutableHandler) h);
        } else {
          registerHandler(h);
        }
      }
    }
  }

  @Override
  public Object handle(Object message) {
    String serverName = ((Request) message).getInterfaceName();
    return handlerMap.getOrDefault(serverName, new DefaultHandler()).handle(message);
  }

  public static class DefaultHandler implements Handler {

    @Override
    public String getServerName() {
      return null;
    }

    @Override
    public Object handle(Object message) {
      throw new UnsupportedOperationException();
    }
  }
}
