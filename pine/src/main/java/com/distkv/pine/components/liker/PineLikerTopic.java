package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;

import com.distkv.common.exception.PineLikerLikeeNotFoundException;
import java.util.HashSet;

public class PineLikerTopic {
  private String topicName;

  private DistkvClient distkvClient;

  private PineLikerTopic() {

  }

  public String getTopicName() {
    return topicName;
  }

  private void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public DistkvClient getDistkvClient() {
    return distkvClient;
  }

  private void setDistkvClient(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  static class Builder {
    private PineLikerTopic pineLikerTopic;

    public Builder() {
      pineLikerTopic = new PineLikerTopic();
    }

    public Builder setTopicKey(String topicKey) {
      pineLikerTopic.setTopicName(topicKey);
      return this;
    }

    public Builder setDistkvClient(DistkvClient distkvClient) {
      pineLikerTopic.setDistkvClient(distkvClient);
      return this;
    }

    public PineLikerTopic build() {
      return pineLikerTopic;
    }
  }

  public void likesFrom(String likee) {
    try {
      distkvClient.sets().get(topicName);
    } catch (KeyNotFoundException e) {
      distkvClient.sets().put(topicName, new HashSet<>());
    }
    distkvClient.sets().putItem(topicName, likee);
  }

  public boolean unLikesFrom(String likee) {
    try {
      distkvClient.sets().get(topicName);
    } catch (KeyNotFoundException e) {
      distkvClient.sets().put(topicName, new HashSet<>());
    }
    try {
      distkvClient.sets().removeItem(topicName, likee);
    } catch (KeyNotFoundException e) {
      throw new PineLikerLikeeNotFoundException(
          "This likee has never liked this topic");
    }
    return true;
  }

  public int count() {
    return distkvClient.sets().get(topicName).size();
  }
}
