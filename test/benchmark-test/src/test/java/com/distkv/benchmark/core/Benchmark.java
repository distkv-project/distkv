package com.distkv.benchmark.core;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {
  private int threadNum = 1;

  private Runnable runnable = null;

  public Benchmark(int threadNum) {
    this.threadNum = threadNum;
  }

  public Benchmark() {
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public int getThreadNum() {
    return threadNum;
  }

  public void run() {
    try {
      if (runnable == null) {
        System.out.println("You must set test module");
      } else {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
          Thread thread = new Thread(runnable);
          threads.add(thread);
          thread.start();
        }
        for (int i = 0; i < threadNum; i++) {
          threads.get(i).join();
        }
      }
    } catch (InterruptedException e) {
      throw new RuntimeException("Failed to start benchmark threads.", e);
    }
  }

  public void setTest(Runnable runnable) {
    this.runnable = runnable;
  }
}
