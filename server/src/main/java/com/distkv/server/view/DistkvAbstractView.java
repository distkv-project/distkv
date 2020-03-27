package com.distkv.server.view;


import java.util.concurrent.ConcurrentHashMap;

public abstract class DistkvAbstractView {

  protected ShardTable shardTable;

  private ConcurrentHashMap<String, NodeTable> map = new ConcurrentHashMap<>();

  protected void put(String key, NodeTable value) {
    map.put(key, value);
  }

  public NodeTable get(String key) {
    return map.get(key);
  }

  public boolean containsKey(String key) {
    return map.containsKey(key);
  }
}
