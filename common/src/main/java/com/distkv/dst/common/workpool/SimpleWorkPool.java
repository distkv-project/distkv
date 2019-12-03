package com.distkv.dst.common.workpool;

import com.distkv.dst.common.queue.DefaultConcurrentQueue;
import com.distkv.dst.common.queue.DstConcurrentQueue;

public class SimpleWorkPool extends AbstractExecutor {

  /*
    The capacity of the thread pool
   */
  private volatile int poolSize = Runtime.getRuntime().availableProcessors() * 2;


  private volatile int coreSize = 0;

  private DstConcurrentQueue<Runnable> dstConcurrentQueue;

  private volatile boolean shutdown = false;

  public SimpleWorkPool() {
    dstConcurrentQueue = new DefaultConcurrentQueue<>();
  }

  public SimpleWorkPool(int poolSize) {
    this.poolSize = poolSize;
    dstConcurrentQueue = new DefaultConcurrentQueue<>();
  }

  public SimpleWorkPool(int poolSize, DstConcurrentQueue<Runnable> queue) {
    this.poolSize = poolSize;
    dstConcurrentQueue = queue;
  }

  private void addWorker() {
    new Thread(new Worker()).start();
    coreSize++;
  }

  public void showdown() {
    shutdown = true;
  }

  @Override
  public void exec(Runnable runnable) {
    if (shutdown) {
      return;
    }
    if (runnable == null) {
      throw new NullPointerException();
    }
    if (coreSize < poolSize) {
      dstConcurrentQueue.offer(runnable);
      addWorker();
    } else {
      dstConcurrentQueue.offer(runnable);
    }
  }

  @Override
  public void shutdown() {
    shutdown = true;
  }

  @Override
  public boolean isShutdown() {
    return shutdown;
  }

  class Worker extends Thread {
    @Override
    public void run() {
      while (!shutdown) {
        dstConcurrentQueue.poll().run();
      }
    }
  }

}
