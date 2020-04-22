package com.distkv.pine.components.cache;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.PineCacheKeyNotFoundException;
import com.distkv.pine.components.AbstractPineHandle;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashSet;
import java.util.Set;

public class PineCacheImpl extends AbstractPineHandle implements PineCache {

  private static final String COMPONENT_TYPE = "CACHE";

  private DistkvClient distkvClient;

  private Long expireTime;

  final Set<String> set = new HashSet<>();

  public PineCacheImpl(DistkvClient distkvClient, Long expireTime) {
    super();
    this.distkvClient = distkvClient;
    this.expireTime = expireTime;
  }

  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public void newItem(String item) {
    try {
      distkvClient.strs().get(item);
      distkvClient.expire(item,expireTime);
      set.add(item);
    } catch (KeyNotFoundException | InvalidProtocolBufferException e) {
      distkvClient.strs().put(item, item);
      distkvClient.expire(item,expireTime);
      set.add(item);
    }

  }

  @Override
  public Boolean isExpired(String item) {
    try {
      if (set.contains(item)) {
        distkvClient.strs().get(item);
      } else {
        throw new PineCacheKeyNotFoundException("This key has never found in cache");
      }
    } catch (KeyNotFoundException | InvalidProtocolBufferException e) {
      throw new PineCacheKeyNotFoundException(
          "This key has never found in cache");
    }
    return false;
  }

}
