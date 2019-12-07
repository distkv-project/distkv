package com.distkv.dmeta.server.statemachine;

import java.util.HashMap;

public class NameSpace implements SpaceInterface {

  private HashMap<String, SpaceInterface> subNode;

  public NameSpace() {
    subNode = new HashMap<>();
  }

  public HashMap<String, SpaceInterface> getCurrentMap() {
    return this.subNode;
  }

  @Override
  public SpaceType getType() {
    return SpaceType.NAME_SPACE;
  }
}
