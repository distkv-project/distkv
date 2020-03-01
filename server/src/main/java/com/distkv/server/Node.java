package com.distkv.server;

import com.distkv.common.id.NodeId;

import java.io.Serializable;

/**
 * the node information
 */
public class Node implements Serializable {

  private static final long serialVersionUID = 1210798805637667776L;

  private NodeId nodeId;
  private Address nodeAddress;
  private Location nodeLocation;

  public static class NodeBuilder {
    private NodeId nodeId;
    private Address nodeAddress;
    private Location nodeLocation;

    public NodeBuilder withNodeId(NodeId nodeId) {
      this.nodeId = nodeId;
      return this;
    }

    public NodeBuilder withNodeAddress(Address nodeAddress) {
      this.nodeAddress = nodeAddress;
      return this;
    }

    public NodeBuilder withNodeLocation(Location nodeLocation) {
      this.nodeLocation = nodeLocation;
      return this;
    }

    public Node build() {
      Node node = new Node();
      node.setNodeId(this.nodeId);
      node.setNodeAddress(this.nodeAddress);
      node.setNodeLocation(this.nodeLocation);
      return node;
    }
  }

  public NodeId getNodeId() {
    return nodeId;
  }

  public void setNodeId(NodeId nodeId) {
    this.nodeId = nodeId;
  }

  public Address getNodeAddress() {
    return nodeAddress;
  }

  public void setNodeAddress(Address nodeAddress) {
    this.nodeAddress = nodeAddress;
  }

  public Location getNodeLocation() {
    return nodeLocation;
  }

  public void setNodeLocation(Location nodeLocation) {
    this.nodeLocation = nodeLocation;
  }
}
