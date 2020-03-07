package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;

import java.util.HashSet;

public class PipeLikerTopic {
  private String topicName;

  private DistkvClient distkvClient;

  private PipeLikerTopic() {

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
    private PipeLikerTopic pipeLikerTopic;

    public Builder() {
      pipeLikerTopic = new PipeLikerTopic();
    }

    public Builder setTopicKey(String topicKey) {
      pipeLikerTopic.setTopicName(topicKey);
      return this;
    }

    public Builder setDistkvClient(DistkvClient distkvClient) {
      pipeLikerTopic.setDistkvClient(distkvClient);
      return this;
    }

    public PipeLikerTopic build() {
      return pipeLikerTopic;
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
    boolean isFind = true;
    try {
      distkvClient.sets().removeItem(topicName, likee);
    } catch (KeyNotFoundException e) {
      isFind = false;
    }
    return isFind;
  }

  public int count() {
    return distkvClient.sets().get(topicName).size();
  }
}
