package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class PipeLikerTopic {
  private String topicKey;

  private DistkvClient distkvClient;

  public PipeLikerTopic(String topicKey, DistkvClient distkvClient) {
    this.topicKey = topicKey;
    this.distkvClient = distkvClient;
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
