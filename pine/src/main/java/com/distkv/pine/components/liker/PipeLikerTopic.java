package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.DistkvException;
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
      distkvClient.sets().drop(topicKey);
    } catch (KeyNotFoundException e) {
      throw new DistkvException("");
    }
    set.add(people);
    distkvClient.sets().put(topicKey, set);
  }

  public boolean unLikesFrom(String people) {
    Set<String> set = null;
    try {
      set = distkvClient.sets().get(topicKey);
    } catch (KeyNotFoundException e) {
      set = new HashSet<>();
    }
    boolean isFind = set.contains(people);
    if (isFind) {
      set.remove(people);
      distkvClient.sets().drop(topicKey);
      if (!set.isEmpty()) {
        distkvClient.sets().put(topicKey, set);
      }
    }
    return isFind;
  }

  public int count() {
    return distkvClient.sets().get(topicKey).size();
  }
}
