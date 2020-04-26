package com.distkv.server.view;

import com.distkv.common.NodeInfo;
import com.distkv.common.id.GroupId;
import java.util.concurrent.ConcurrentHashMap;

public class DistkvGlobalView extends DistkvAbstractView {

  public DistkvGlobalView() {
    shardTable = new ShardTable();
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

  public ConcurrentHashMap<String, NodeTable> getGlobalViewTable() {
    return this.map;
  }

}
