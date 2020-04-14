package com.distkv.pine.components.cache;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.pine.components.AbstractPineHandle;
import com.google.protobuf.InvalidProtocolBufferException;

public class PineCacheImpl extends AbstractPineHandle implements PineCache {

  private static final String COMPONENT_TYPE = "CACHE";

  private DistkvClient distkvClient;

  private Long expireTime;

  public PineCacheImpl(DistkvClient distkvClient, Long expireTime) {
    super();
    this.distkvClient = distkvClient;
    this.expireTime = expireTime;
    distkvClient.expire(getKey(),expireTime);
  }

  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public void newItem(String item) {
    distkvClient.strs().put(getKey(), item);
    distkvClient.expire(getKey(),expireTime);
  }

  @Override
  public Boolean isExpired(String item) {
    try {
      distkvClient.strs().get(item);
    } catch (KeyNotFoundException | InvalidProtocolBufferException e) {
      return false;
    }
    try {
      distkvClient.drop(item);
    } catch (KeyNotFoundException e) {
      throw new KeyNotFoundException(
          "This key is drop");
    }
    return true;
  }

  @Override
  public String getItem(String newItems) {
    try {
      String newItem = distkvClient.strs().get(getKey());
      return newItem;
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      return "";
    }
  }
}
