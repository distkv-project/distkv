package org.dst.rpc.transport.api;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.dst.rpc.core.codec.Codec;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.common.constants.GlobalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端
 */
public abstract class AbstractServer implements Server {

  private static Logger logger = LoggerFactory.getLogger(AbstractServer.class);

  private static final int NEW = 0;
  private static final int CONNECTED = 1;
  private static final int DISCONNECTED = 2;

  private URL serverUrl;
  private volatile int status = NEW;
  private Codec codec;

  /**
   * Server只要存在就不为null
   */
  private RoutableHandler routableHandler;
  private ExecutorService executor;

  public AbstractServer(URL url,Codec codec) {
    serverUrl = url;
    this.codec = codec;
    routableHandler = new DefaultRoutableHandler();
    /*
      fixme 这是一个简单的线程池实现，不阻塞网络io线程，这里就是不阻塞netty的io线程，线程数应该要大一点，dubbo默认是200，这个参数需要从url读取
     */
    executor = Executors.newFixedThreadPool(GlobalConstants.threadNumber * 2);
  }

  @Override
  public Codec getCodec() {
    return codec;
  }

  @Override
  public RoutableHandler getRoutableHandler() {
    return routableHandler;
  }

  @Override
  public Executor getExecutor() {
    return executor;
  }

  @Override
  public URL getUrl() {
    return serverUrl;
  }

  @Override
  public boolean isOpen() {
    return status == CONNECTED;
  }

  @Override
  public void open() {
    doOpen();
  }

  @Override
  public void close() {
    doClose();
  }

  protected abstract void doOpen();
  protected abstract void doClose();
}
