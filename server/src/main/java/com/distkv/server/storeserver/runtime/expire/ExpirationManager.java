package com.distkv.server.storeserver.runtime.expire;

import com.distkv.common.exception.DistkvException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.distkv.server.storeserver.StoreConfig;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpirationManager {

  private static Logger LOGGER = LoggerFactory.getLogger(ExpirationManager.class);

  private static final Integer DEFAULT_CAPACITY = 2048;

  //Scheduled cleaning tasks.
  ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

  private StoreConfig storeConfig;

  /**
   * PriorityQueue allows the data with the lowest expiration time to be queued. Just look at the
   * cached most recent expired data and avoid scanning all caches.
   */
  public PriorityQueue<Node> expirationQueue = new PriorityQueue<>(DEFAULT_CAPACITY);

  public ExpirationManager() {

    /*
     * Use the default scheduled executor to clear outdated data every 1 seconds.
     */
    scheduledExecutor.scheduleAtFixedRate(new SwapExpiredNode(), 0, 1, TimeUnit.SECONDS);
  }

  /**
   * The expire request will be stored in the queue as a Node object. This internal Node object
   * contains the key, expiration time and request type.
   *
   * @param request A expire request.
   * @param storeConfig Local server config information.
   */
  public void addToCycle(DistkvRequest request, StoreConfig storeConfig) {
    this.storeConfig = storeConfig;
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
    expirationQueue.offer(new Node(key, requestType, expireTime));
  }

  private class SwapExpiredNode implements Runnable {

    @Override
    public void run() {
      long now = System.currentTimeMillis();
      while (true) {
        Node node = expirationQueue.peek();
        if (node == null || node.expireTime > now) {
          return;
        }
        expirationQueue.poll();
        clearStore(node);
      }
    }
  }

  /**
   * When the timer detects that a key in the queue has expired, it clears it. The expired client
   * establishes a connection with StoreServer and sends a drop operation.
   *
   * @param node A node object that needs to be cleaned up.
   */
  private void clearStore(Node node) {
    ExpireClient expireClient = new DefaultExpireClient(storeConfig);
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
