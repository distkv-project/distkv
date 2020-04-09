package com.distkv.pine.components.cache;

import com.distkv.client.DistkvClient;
import com.distkv.pine.components.AbstractPineHandle;

public class PineCacheImpl extends AbstractPineHandle implements PineCache {

  private static final String COMPONENT_TYPE = "LIKER";

  private DistkvClient distkvClient;

  private Long expireTime;

  public PineCacheImpl(DistkvClient distkvClient, Long expireTime) {
    this.distkvClient = distkvClient;
    this.expireTime = expireTime;
  }

  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public PineCacheTopic topic(String topic) {
    return new PineCacheTopic.Builder()
        .setTopicKey(getKey(topic))
        .setDistkvClient(distkvClient)
        .setExpireTime(expireTime)
        .build();
  }

  private String getKey(String topic) {
    return String.format("%s_%s", super.getKey(), topic);
  }


}
