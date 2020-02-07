package com.distkv.benchmark;

import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DSTBenchmark {
  public static final String serverAddress = "distkv://127.0.0.1:8082";

  private int threadNum = 1;

  private Consumer<DistkvClient> testModule;

  public DSTBenchmark(int threadNum) {
    this.threadNum = threadNum;
  }

  public DSTBenchmark() {
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public int getThreadNum() {
    return threadNum;
  }

  public void setTestModule(Consumer<DistkvClient> testModule) {
    this.testModule = testModule;
  }

  public void run() {
    try {
      List<DistkvClient> clients = new ArrayList<>();
      for (int i = 0; i < getThreadNum(); i++) {
        clients.add(new DefaultDistkvClient(serverAddress));
      }
      List<Thread> threads = new ArrayList<>();
      for (int i = 0; i < clients.size(); i++) {
        int finalI = i;
        Thread thread = new Thread(() -> testModule.accept(clients.get(finalI)));
        thread.start();
        threads.add(thread);
      }
      for (int i = 0; i < getThreadNum(); i++) {
        threads.get(i).join();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException("Failed to start benchmark threads.", e);
    }
  }
}
