package com.distkv.server.storeserver.runtime.expire;

import com.distkv.core.KVStore;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ExpireCycle {

  private ConcurrentHashMap<String, Node> strMap;

  private static ScheduledExecutorService swapExpiredPool
      = new ScheduledThreadPoolExecutor(10);

  private ReentrantLock lock = new ReentrantLock();
  /**
   * Store engine.
   */
  private KVStore storeEngine;

  /*
   *  PriorityQueue allows the data with the lowest expiration time to be queued.
   *  Just look at the cached most recent expired data and avoid scanning all caches.
   */
  public PriorityQueue<Node> expireQueue = new PriorityQueue<>(1024);

  public ExpireCycle(KVStore kvStore) {
    storeEngine = kvStore;
    this.strMap = new ConcurrentHashMap<>();
    /*
     * Use the default thread pool to clear outdated data every 5 seconds.
     * The thread pool and the calling frequency can be set by the method overload to the caller.
     */
    swapExpiredPool.scheduleWithFixedDelay(new SwapExpiredNodeWork(), 5, 5, TimeUnit.SECONDS);
  }

  private class SwapExpiredNodeWork implements Runnable {

    @Override
    public void run() {
      long now = System.currentTimeMillis();
      while (true) {
        lock.lock();
        try {
          Node node = expireQueue.peek();
          if (node == null || node.expireTime > now) {
            return;
          }
          strMap.remove(node.key);
          //发送删除指令。
          expireQueue.poll();
        } finally {
          lock.unlock();
        }
      }
    }
  }

  private static class Node implements Comparable<Node> {

    private String key;
    private RequestType requestType;
    private long expireTime;

    public Node(String key, RequestType requestType, long expireTime) {
      this.key = key;
      this.requestType = requestType;
      this.expireTime = expireTime;
    }

    @Override
    public int compareTo(Node o) {
      long r = this.expireTime - o.expireTime;
      if (r > 0) {
        return 1;
      }
      if (r < 0) {
        return -1;
      }
      return 0;
    }
  }
}
