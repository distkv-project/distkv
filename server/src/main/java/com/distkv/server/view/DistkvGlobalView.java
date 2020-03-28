package com.distkv.server.view;

import com.distkv.common.NodeInfo;
import com.distkv.common.id.GroupId;

public class DistkvGlobalView extends DistkvAbstractView {

  public DistkvGlobalView() {
    shardTable = new ShardTable();
  }

  @Override
  public void put(String key, NodeTable value) {
    super.put(key, value);
  }

  public void putNode(NodeInfo nodeInfo) {
    if (nodeInfo.getNodeId().getGroupId() == null) {
      nodeInfo.getNodeId().setGroupId(GroupId.fromIndex((short) 1));
    }
    String groupIndex = String.valueOf(nodeInfo.getNodeId().getGroupId().getIndex());
    if (!containsKey(groupIndex)) {
      put(groupIndex, new NodeTable());
    }
    NodeTable table = get(groupIndex);
    table.putNodeInfo(nodeInfo);
  }

}
