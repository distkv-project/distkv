package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;

public class PineLikerImpl implements PineLiker {

  private static final String COMPONENT_TYPE = "LIKER";

  private DistkvClient distkvClient;

  public PineLikerImpl(
      DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  protected String getComponentType() {
    return COMPONENT_TYPE;
  }

  @Override
  public PipeLikerTopic getTopic(String topic) {
   return new PipeLikerTopic(getKey(topic), distkvClient);
  }

  private String getKey(String topic) {
    return String.format("%s_%s", getComponentType(), topic);
  }

}
