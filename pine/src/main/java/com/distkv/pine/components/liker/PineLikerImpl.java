package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.pine.components.AbstractPineHandle;

public class PineLikerImpl extends AbstractPineHandle implements PineLiker {

  private static final String COMPONENT_TYPE = "LIKER";

  private DistkvClient distkvClient;

  public PineLikerImpl(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public PineLikerTopic topic(String topic) {
    return new PineLikerTopic.Builder()
        .setTopicKey(getKey(topic))
        .setDistkvClient(distkvClient)
        .build();
  }

  private String getKey(String topic) {
    return String.format("%s_%s", super.getKey(), topic);
  }

}
