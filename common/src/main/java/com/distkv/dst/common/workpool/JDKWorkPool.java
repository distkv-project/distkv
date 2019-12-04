package com.distkv.dst.common.workpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.Executors;

public class JDKWorkPool extends AbstractExecutor {

  private ThreadPoolExecutor threadPoolExecutor;

  public JDKWorkPool(int corePoolSize,
                     int maximumPoolSize,
                     long keepAliveTime,
                     TimeUnit unit,
                     BlockingQueue<Runnable> workQueue) {
    threadPoolExecutor = new ThreadPoolExecutor(
          corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public JDKWorkPool(int corePoolSize,
                     int maximumPoolSize,
                     long keepAliveTime,
                     TimeUnit unit,
                     BlockingQueue<Runnable> workQueue,
                     ThreadFactory threadFactory) {
    threadPoolExecutor = new ThreadPoolExecutor(
          corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
  }

  /**
   * Return JDK ExecutorService newCachedThreadPool()
   *
   * @return JDK ExecutorService
   */
  public ExecutorService newCachedThreadPool() {
    return Executors.newCachedThreadPool();
  }

  /**
   * Return JDK ExecutorService newFixedThreadPool()
   *
   * @return JDK ExecutorService
   */
  public ExecutorService newFixedThreadPool(int threadNum, ThreadFactory threadFactory) {
    return Executors.newFixedThreadPool(threadNum, threadFactory);
  }

  /**
   * Return JDK ExecutorService newSingleThreadExecutor()
   *
   * @return JDK ExecutorService
   */
  public ExecutorService newSingleThreadExecutor() {
    return Executors.newSingleThreadExecutor();
  }

  @Override
  public void exec(Runnable runnable) {
    threadPoolExecutor.execute(runnable);
  }

  @Override
  public void shutdown() {
    threadPoolExecutor.shutdown();
  }

  @Override
  public boolean isShutdown() {
    return threadPoolExecutor.isShutdown();
  }
}
