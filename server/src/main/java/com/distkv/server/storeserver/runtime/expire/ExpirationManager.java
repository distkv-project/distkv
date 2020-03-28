package com.distkv.server.storeserver.runtime.expire;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.ExpireProtocol.ExpireRequest;
import com.distkv.server.storeserver.StoreConfig;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.Date;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpirationManager {

  private static Logger LOGGER = LoggerFactory.getLogger(ExpirationManager.class);

  private static final Integer DEFAULT_CAPACITY = 2048;

  //Scheduled cleaning tasks.
  private static ScheduledExecutorService scheduledExecutor = Executors
      .newSingleThreadScheduledExecutor();

  private ExpirationClient expireClient;
  /**
   * PriorityQueue allows the data with the lowest expiration time to be queued. Just look at the
   * cached most recent expired data and avoid scanning all caches.
   */
  public PriorityQueue<Node> expirationQueue = new PriorityQueue<>(DEFAULT_CAPACITY);

  public ExpirationManager(StoreConfig storeConfig) {
    expireClient = new ExpirationClient(storeConfig);
    /*
     * Use the default scheduled executor to clear outdated data every 1 seconds.
     */
    scheduledExecutor.scheduleAtFixedRate(new SwapExpiredNode(), 1, 1, TimeUnit.SECONDS);
  }

  /**
   * The expire request will be stored in the queue as a Node object. This internal Node object
   * contains the key, expiration time and request type.
   *
   * @param request A expire request.
   */
  public void addToCycle(DistkvRequest request) {
    String key = request.getKey();
    long expiredTime = -1;
    try {
      ExpireRequest expireRequest = request.getRequest().unpack(ExpireRequest.class);
      expiredTime = expireRequest.getExpireTime() + System.currentTimeMillis();
    } catch (InvalidProtocolBufferException e) {
      LOGGER.error("Failed to unpack ExpireRequest {1}", e);
      throw new DistkvException(e.toString());
    }
    expirationQueue.offer(new Node(key, expiredTime));
  }

  /**
   * Get the remaining survival time of a key. For keys with no expiration time set and stored in
   * the store, it will return -1.
   *
   * @param key The key to get time to live.
   * @return Survival time for a key.
   */
  public long survivalTime(String key) {
    Optional<Node> nodeOptional = expirationQueue.stream().filter(item -> item.key.equals(key))
        .findFirst();
    if (nodeOptional.isPresent()) {
      Node node = nodeOptional.get();
      return node.expireTime - new Date().getTime();
    } else {
      try {
        expireClient.connect();
        try {
          boolean exists = expireClient.exists(key);
          if (exists) {
            return -1;
          } else {
            throw new KeyNotFoundException("The key {} is not found in the store.", key);
          }
        } catch (ExecutionException | InterruptedException | InvalidProtocolBufferException e) {
          LOGGER.error("Failed to query if the key {} exists {}", key, e);
          throw new DistkvException(e.toString());
        }
      } finally {
        if (!expireClient.isConnected()) {
          expireClient.disconnect();
        }
      }
    }
  }

  /**
   * This is a task for clean expired key. It will take all expired node from PriorityQueue, and
   * drop them in store.
   */
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
    try {
      expireClient.connect();
      expireClient.drop(node.key);
    } finally {
      if (!expireClient.isConnected()) {
        expireClient.disconnect();
      }
    }
  }

  /**
   * A Node object to store information about invalid key settings. The object has key, requestType
   * and expiration time three attributes. Implemented the Comparable interface, rewritten the
   * compareTo method, and achieved the comparison of expiredTime.
   */
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
