package com.distkv.pine.components.cache;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.pine.components.AbstractPineHandle;
import com.google.protobuf.InvalidProtocolBufferException;

public class PineCacheImpl extends AbstractPineHandle implements PineCache {

  private static final String COMPONENT_TYPE = "Cache";

  private DistkvClient distkvClient;

  public PineCacheImpl(DistkvClient distkvClient, Long expiredTime) {
    this.distkvClient = distkvClient;
    distkvClient.strs().expire(getKey(),expiredTime);
  }

  public PineCacheImpl(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  @Override
  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public void newItem(String newItems) {
    distkvClient.strs().put(newItems, newItems);
  }

  @Override
  public void newItem(String newItems, long expireTime) {
    distkvClient.strs().put(newItems, newItems);
    distkvClient.strs().expire(newItems, expireTime);
  }

  @Override
  public Boolean expire(String newItems) {
    try {
      String newItem = distkvClient.strs().get(newItems);
      if (newItem.isEmpty()) {
        return true;
      } else {
        return false;
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvException("InvalidProtocolBufferException");
    } catch (KeyNotFoundException e) {
      throw new DistkvException(newItems + ": is not found");
    }
  }
}
