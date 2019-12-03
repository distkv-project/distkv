package com.distkv.dst.common.workpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class DrpcThreadPool {

  private volatile boolean shutdown;
  private static final int DEFAULT_QUEUE_SIZE = 1024;

  private ArrayDeque<Runnable> produced;
  private ArrayDeque<Runnable> toConsume;
  private ReentrantLock consumerLock;
  private ReentrantLock producerLock;
  private Condition isProducerNotEmptyCondition;
  private ArrayList<Thread> threads;


  public DrpcThreadPool(int initialThreadNum) {
    this(initialThreadNum, 0);
  }

  public DrpcThreadPool(int initialThreadNum, int queueSize) {
    if (initialThreadNum <= 0) {
      throw new IllegalArgumentException(
            String.format("DrpcThreadPool initialThreadNum %s should be positive ", initialThreadNum));
    }
    threads = new ArrayList<>(initialThreadNum);
    shutdown = false;

    if (queueSize <= 0) {
      queueSize = DEFAULT_QUEUE_SIZE;
    }
    produced = new ArrayDeque<>(queueSize);
    toConsume = new ArrayDeque<>(queueSize);
    consumerLock = new ReentrantLock();
    producerLock = new ReentrantLock();
    isProducerNotEmptyCondition = producerLock.newCondition();
    for (int i = 0; i < initialThreadNum; ++i) {
      Thread thread = new Thread(() -> consume());
      thread.start();
      threads.add(thread);
    }
  }

  private void consume() {
    while (true) {
      Runnable task = null;
      while (true) {
        consumerLock.lock();
        try {
          if (!toConsume.isEmpty()) {
            task = toConsume.pop();
            break;
          }
        } finally {
          consumerLock.unlock();
        }

        producerLock.lock();
        try {
          while (!shutdown && produced.isEmpty()) {
            try {
              isProducerNotEmptyCondition.await();
            } catch (InterruptedException ex) {
              // ignore
            }
          }
          if (!produced.isEmpty()) {
            consumerLock.lock();
            try {
              ArrayDeque<Runnable> tmp = produced;
              produced = toConsume;
              toConsume = tmp;
            } finally {
              consumerLock.unlock();
            }
          } else {
            // stopped must be true
            break;
          }
        } finally {
          producerLock.unlock();
        }
      }
      if (task != null) {
        task.run();
      } else {
        // The thread pool was shut down
        break;
      }
    }
  }

  public void shutdowm() {
    shutdown = true;
    producerLock.lock();
    try {
      isProducerNotEmptyCondition.signalAll();
    } finally {
      producerLock.unlock();
    }
  }

  public boolean submit(Runnable task) {
    Runnable[] tasks = {task};
    return submit(tasks, 0, 1) == 1;
  }

  public long submit(Runnable[] tasks, int offset, int len) {
    int cur = offset;
    int end = offset + len;
    while (!shutdown && cur < end) {
      producerLock.lock();
      try {
        int toProduce = end - cur;
        if (toProduce > 0) {
          boolean wasEmpty = produced.isEmpty();
          int last = cur + toProduce;
          for (int i = offset; i < last; i++) {
            produced.addLast(tasks[i]);
          }
          if (wasEmpty) {
            isProducerNotEmptyCondition.signalAll();
          }
        }
        cur += toProduce;
      } finally {
        producerLock.unlock();
      }
    }
    return cur - offset;
  }

  public boolean isShutdowm() {
    return shutdown;
  }

}

