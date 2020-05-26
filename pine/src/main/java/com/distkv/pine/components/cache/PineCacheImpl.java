package com.distkv.pine.components.cache;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.pine.components.AbstractPineHandle;
import com.google.protobuf.InvalidProtocolBufferException;

public class PineCacheImpl extends AbstractPineHandle implements PineCache {

  private static final String COMPONENT_TYPE = "CACHE";

  private DistkvClient distkvClient;

  private Long defaultExpireTime;

  public PineCacheImpl(DistkvClient distkvClient, Long expireTime) {
    super();
    this.distkvClient = distkvClient;
    this.defaultExpireTime = expireTime;
  }

  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public void newItem(String item) {
    try {
      distkvClient.strs().get(item);
      distkvClient.expire(item, defaultExpireTime);
    } catch (KeyNotFoundException  e) {
      distkvClient.strs().put(item, item);
      distkvClient.expire(item, defaultExpireTime);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Boolean isExpired(String item) {
    try {
      distkvClient.strs().get(item);
    } catch (KeyNotFoundException e) {
      return true;
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

}

