package com.distkv.pine.components.liker;

import com.distkv.client.DistkvClient;
import com.distkv.common.exception.KeyNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class PineLikerImpl extends AbstractPineLiker implements PineLiker {

  private static final String COMPONENT_TYPE = "LIKER";

  private DistkvClient distkvClient;

  public PineLikerImpl(
      DistkvClient distkvClient, String people, String content) {
    super(people, content);
    this.distkvClient = distkvClient;
    try {
      Set<String> set = distkvClient.sets().get(getKey());
    } catch (KeyNotFoundException e) {
      distkvClient.sets().put(getKey(), new HashSet<>());
    }
  }

  @Override
  public void likesFrom(
      String fromPeople) {
    Set<String> set = distkvClient.sets().get(getKey());
    if (!set.contains(fromPeople)) {
      set.add(fromPeople);
      distkvClient.sets().drop(getKey());
      distkvClient.sets().put(getKey(), set);
    } else {
      this.unLikesFrom(fromPeople);
    }
  }

  @Override
  public void unLikesFrom(
      String fromPeople) {
    Set<String> set = distkvClient.sets().get(getKey());
    if (set.contains(fromPeople)) {
      set.remove(fromPeople);
      distkvClient.sets().drop(getKey());
      distkvClient.sets().put(getKey(), set);
    } else {
      this.likesFrom(fromPeople);
    }
  }

  @Override
  public int count() {
    Set<String> set = distkvClient.sets().get(getKey());
    return set.size();
  }

  @Override
  protected String getComponentType() {
    return COMPONENT_TYPE;
  }
}
