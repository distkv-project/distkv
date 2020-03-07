package com.distkv.server.storeserver.runtime.expire;

import com.distkv.common.exception.DistkvException;
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
      = new ScheduledThreadPoolExecutor(1);

  private ReentrantLock lock = new ReentrantLock();

  /*
   *  PriorityQueue allows the data with the lowest expiration time to be queued.
   *  Just look at the cached most recent expired data and avoid scanning all caches.
   */
  public PriorityQueue<Node> expireQueue = new PriorityQueue<>(1024);

  public ExpireCycle() {
    /*
     * Use the default thread pool to clear outdated data every 1 seconds.
     */
    swapExpiredPool.scheduleWithFixedDelay(new SwapExpiredNode(), 1, 1, TimeUnit.SECONDS);
  }

  public void addToCycle(DistkvRequest request) {
    String key = request.getKey();
    RequestType requestType = request.getRequestType();
    long expireTime = -1;
    try {
      ExpireRequest expireRequest = request.getRequest().unpack(ExpireRequest.class);
      expireTime = expireRequest.getExpireTime() * 1000 + System.currentTimeMillis();
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
    ExpireClient expireClient = new DefaultExpireClient();
    expireClient.connect();
    RequestType requestType = node.requestType;
    String key = node.key;
    switch (requestType) {
      case EXPIRED_STR:
        expireClient.strDrop(key);
        break;
      case EXPIRED_LIST:
        expireClient.listDrop(key);
        break;
      case EXPIRED_SET:
        expireClient.setDrop(key);
        break;
      case EXPIRED_DICT:
        expireClient.dictDrop(key);
        break;
      case EXPIRED_INT:
        expireClient.intDrop(key);
        break;
      case EXPIRED_SLIST:
        expireClient.slistDrop(key);
        break;
      default: {
        break;
      }
    }
    expireClient.disconnect();
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
