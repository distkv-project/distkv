package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class PipeLikerTopic {

  public String getTopicKey() {
    return topicKey;
  }

  public void setTopicKey(String topicKey) {
    this.topicKey = topicKey;
  }

  public DistkvClient getDistkvClient() {
    return distkvClient;
  }

  public void setDistkvClient(DistkvClient distkvClient) {
    this.distkvClient = distkvClient;
  }

  static class Builder {
    private PipeLikerTopic pipeLikerTopic;

    public Builder() {
      pipeLikerTopic = new PipeLikerTopic();
    }

    public Builder setTopicKey(String topicKey) {
      pipeLikerTopic.setTopicKey(topicKey);
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

  private String topicKey;

  private DistkvClient distkvClient;

  private PipeLikerTopic() {

  }

  public void likesFrom(String people) {
    Set<String> set = new HashSet<>();
    try {
      set = distkvClient.sets().get(topicKey);
    } catch (KeyNotFoundException e) {
      distkvClient.sets().put(topicKey, new HashSet<>());
    }
    distkvClient.sets().drop(topicKey);
    set.add(people);
    distkvClient.sets().put(topicKey, set);
  }

  public boolean unLikesFrom(String people) {
    Set<String> set = new HashSet<>();
    try {
      set = distkvClient.sets().get(topicKey);
    } catch (KeyNotFoundException e) {
      distkvClient.sets().put(topicKey, new HashSet<>());
    }
    boolean isFind = set.contains(people);
    if (isFind) {
      set.remove(people);
      distkvClient.sets().drop(topicKey);
      distkvClient.sets().put(topicKey, set);
    }
    return isFind;
  }

  public int count() {
    return distkvClient.sets().get(topicKey).size();
  }
}
