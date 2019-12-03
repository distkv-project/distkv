package com.distkv.dst.common.workpool;


public class DefaultWorkPool extends AbstractExecutor {

  private DrpcThreadPool drpcThreadPool;

  public DefaultWorkPool (int initialThreadNum) {
    drpcThreadPool =new DrpcThreadPool(initialThreadNum);
  }

  public DefaultWorkPool (int initialThreadNum, int queueSize) {
    drpcThreadPool =new DrpcThreadPool(initialThreadNum,queueSize);
  }

  public boolean submit(Runnable runnable) {
    return drpcThreadPool.submit(runnable);
  }

  public long submit(Runnable[] tasks, int offset, int len) {
    return drpcThreadPool.submit(tasks,offset,len);
  }

  @Override
  public void exec(Runnable runnable) {
  }

  @Override
  public void shutdown() {
    drpcThreadPool.shutdowm();
  }

  @Override
  public boolean isShutdown() {
    return drpcThreadPool.isShutdowm();
  }
}
