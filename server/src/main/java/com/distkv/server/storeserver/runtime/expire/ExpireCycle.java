package com.distkv.server.storeserver.runtime.expire;

import com.distkv.common.exception.DistkvException;
import com.distkv.core.KVStore;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.PriorityQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpireCycle {

  private static Logger LOGGER = LoggerFactory.getLogger(ExpireCycle.class);

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

    /*
     * Use the default thread pool to clear outdated data every 5 seconds.
     * The thread pool and the calling frequency can be set by the method overload to the caller.
     */
    swapExpiredPool.scheduleWithFixedDelay(new SwapExpiredNode(), 5, 10, TimeUnit.SECONDS);
  }

  public void addToCycle(DistkvRequest request) {
    String key = request.getKey();
    RequestType requestType = request.getRequestType();
    long expireTime = -1;
    try {
      ExpireRequest expireRequest = request.getRequest().unpack(ExpireRequest.class);
      expireTime = expireRequest.getExpireTime();
    } catch (InvalidProtocolBufferException e) {
      LOGGER.error("Failed to set expire for a key :{1}", e);
      throw new DistkvException(e.toString());
    }
    expireQueue.offer(new Node(key, requestType, expireTime));
  }

  private class SwapExpiredNode implements Runnable {

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
          expireQueue.poll();
          clearStore(node);
        } finally {
          lock.unlock();
        }
      }
    }
  }

  private void clearStore(Node node) {
    RequestType requestType = node.requestType;
    String key = node.key;
    switch (requestType) {
      case EXPIRED_STR:
        storeEngine.strs().drop(key);
        break;
      case EXPIRED_LIST:
        storeEngine.lists().drop(key);
        break;
      case EXPIRED_SET:
        storeEngine.sets().drop(key);
        break;
      case EXPIRED_DICT:
        storeEngine.dicts().drop(key);
        break;
      case EXPIRED_INT:
        storeEngine.ints().drop(key);
        break;
      case EXPIRED_SLIST:
        storeEngine.sortLists().drop(key);
        break;
      default: {
        break;
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
