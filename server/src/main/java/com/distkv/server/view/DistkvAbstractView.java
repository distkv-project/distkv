package com.distkv.server.view;


import java.util.concurrent.ConcurrentHashMap;

public abstract class DistkvAbstractView {

  protected ShardTable shardTable;

  /**
   * Map storing node table.
   */
  protected ConcurrentHashMap<String, NodeTable> map = new ConcurrentHashMap<>();

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
