package org.dst.core.operatorImpl;

import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.dst.core.operatorset.DstString;

public class DstStringImpl implements DstString {

  private ConcurrentHashMap<String, Node> strMap;

  private static ScheduledExecutorService swapExpiredPool
        = new ScheduledThreadPoolExecutor(10);

  private ReentrantLock lock = new ReentrantLock();

  /*
   *  PriorityQueue allows the data with the lowest expiration time to be queued.
   *  Just look at the cached most recent expired data and avoid scanning all caches.
   */
  private PriorityQueue<Node> expireQueue = new PriorityQueue<>(1024);

  public DstStringImpl() {
    this.strMap = new ConcurrentHashMap<>();
    /*
     * Use the default thread pool to clear outdated data every 5 seconds.
     * The thread pool and the calling frequency can be set by the method overload to the caller.
     */
    swapExpiredPool.scheduleWithFixedDelay(new SwapExpiredNodeWork(), 5, 5, TimeUnit.SECONDS);
  }

  @Override
  public void put(String key, String value) {
    strMap.put(key, new Node(key, -1));
  }

  @Override
  public void put(String key, String value, long ttl) {
    long expireTime = System.currentTimeMillis() + ttl;
    Node newNode = new Node(key, expireTime);
    lock.lock();
    try {
      strMap.put(key, newNode);
      expireQueue.add(newNode);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public String get(String key) {
    if (!strMap.containsKey(key)) {
      return null;
    }
    return strMap.get(key).key;
  }

  @Override
  public boolean del(String key) {
    lock.lock();
    try {
      if (!strMap.containsKey(key)) {
        return false;
      }
      Node node = strMap.remove(key);
      expireQueue.remove(node);
      return true;
    } finally {
      lock.unlock();
    }
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
          expireQueue.poll();
        } finally {
          lock.unlock();
        }
      }
    }
  }

  private static class Node implements Comparable<Node> {
    private String key;
    private long expireTime;

    public Node(String key, long expireTime) {
      this.key = key;
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
