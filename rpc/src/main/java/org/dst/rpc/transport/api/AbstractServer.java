package org.dst.rpc.transport.api;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.common.constants.GlobalConstants;

/**
 * 服务端
 */
public abstract class AbstractServer extends AbstractEndpoint implements Server {

  /**
   * Server只要存在就不为null
   */
  private RoutableHandler routableHandler;
  private ExecutorService executor;

  public AbstractServer(URL url) {
    super(url);
    routableHandler = new DefaultRoutableHandler();
    /*
      fixme 这是一个简单的线程池实现，不阻塞网络io线程，这里就是不阻塞netty的io线程，线程数应该要大一点，dubbo默认是200，这个参数需要从url读取
     */
    executor = Executors.newFixedThreadPool(GlobalConstants.threadNumber * 2);
    //executor = null;
  }

  @Override
  public RoutableHandler getRoutableHandler() {
    return routableHandler;
  }

  @Override
  public Executor getExecutor() {
    return executor;
  }
}
