package org.dst.rpc.transport.api;

import org.dst.rpc.core.common.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public abstract class AbstractEndpoint implements Endpoint {

  private static Logger logger = LoggerFactory.getLogger(AbstractEndpoint.class);

  private static final int NEW = 0;
  private static final int CONNECTED = 1;
  private static final int DISCONNECTED = 2;

  private URL serverUrl;
  private Channel channel;
  private volatile int status = NEW;

  public AbstractEndpoint(URL serverUrl) {
    this.serverUrl = serverUrl;
  }

  @Override
  public URL getUrl() {
    return serverUrl;
  }

  @Override
  public boolean isConnected() {
    return status == CONNECTED;
  }

  @Override
  public void init() {
    status = CONNECTED;
    try {
      channel = createChannel();
      channel.open();
    } catch (Exception e) {
      logger.error("error", e);
      if (channel != null && channel.isOpen()) {
        try {
          channel.close();
        } catch (Exception e1) {
          logger.error("error", e1);
        }
      }
      channel = null;
      status = DISCONNECTED;
    }
  }

  @Override
  public Channel getChannel() {
    return channel;
  }

  @Override
  public void destroy() {
    status = DISCONNECTED;
    channel.close();
    channel = null;
  }

  protected abstract Channel createChannel();
}
