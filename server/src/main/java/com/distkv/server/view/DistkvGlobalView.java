package com.distkv.server.view;

public class DistkvGlobalView extends DistkvAbstractView {

  public DistkvGlobalView() {
    // Initial the global number to 1.
    globalTable = new GlobalTable(1);
    nodeTable = new NodeTable();
    shardTable = new ShardTable();
  }

  @Override
  public void put(String key, String value) {
    super.put(key, value);
  }

}
