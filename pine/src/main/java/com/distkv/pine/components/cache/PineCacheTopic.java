package com.distkv.pine.components.cache;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;
import com.google.protobuf.InvalidProtocolBufferException;

public class PineCacheTopic {
  private String topicName;

  private DistkvClient distkvClient;

  private Long expireTime;

  private PineCacheTopic() {

  }


  private void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public String getTopicName() {
    return topicName;
  }

  private void setTopicName(String topicName) {
    this.topicName = topicName;
  }


  private void setDistkvClient(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  static class Builder {
    private PineCacheTopic pineCacheTopic;

    public Builder() {
      pineCacheTopic = new PineCacheTopic();
    }

    public Builder setTopicKey(String topicKey) {
      pineCacheTopic.setTopicName(topicKey);
      return this;
    }

    public Builder setDistkvClient(DistkvClient distkvClient) {
      pineCacheTopic.setDistkvClient(distkvClient);
      return this;
    }

    public Builder setExpireTime(Long expireTime) {
      pineCacheTopic.setExpireTime(expireTime);
      return this;
    }

    public PineCacheTopic build() {
      return pineCacheTopic;
    }
  }


  public void newItem(String newItems) {
    distkvClient.strs().put(getTopicName(), newItems);
    distkvClient.expire(getTopicName(),expireTime);
  }


  public String getItem(String newItems)   {
    try {
      String newItem = distkvClient.strs().get(getTopicName());
      return newItem;
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * Judgment key expire
   * @param newItems set newItems
   * @return true is expire
   */
  public Boolean expire(String newItems) {
    try {
      distkvClient.strs().get(topicName);
    } catch (KeyNotFoundException | InvalidProtocolBufferException e) {
      return false;
    }
    try {
      distkvClient.drop(topicName);
    } catch (KeyNotFoundException e) {
      throw new KeyNotFoundException(
          "This key is drop");
    }
    return true;
  }




}
