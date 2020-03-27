package com.distkv.server.view;

import com.distkv.common.NodeInfo;

public class DistkvGlobalView extends DistkvAbstractView {

  public DistkvGlobalView() {
    shardTable = new ShardTable();
  }

  @Override
  public void put(String key, NodeTable value) {
    super.put(key, value);
  }

  public void putNode(NodeInfo nodeInfo) {
    String groupIndex = String.valueOf(nodeInfo.getNodeId().getGroupId().getIndex());
    if (!containsKey(groupIndex)) {
      put(groupIndex, new NodeTable());
    }
    NodeTable table = get(groupIndex);
    table.put(nodeInfo);
  }

}
