package com.distkv.server;

import java.io.Serializable;

/**
 * the node information
 */
public class Node implements Serializable {

  private static final long serialVersionUID = 1210798805637667776L;

  private String nodeId;
  private Address address;
  private Location location;

  public static class NodeBuilder {
    private String nodeId;
    private Address address;
    private Location location;

    public NodeBuilder withNodeId(String nodeId) {
      this.nodeId = nodeId;
      return this;
    }

    public NodeBuilder withAddress(Address address) {
      this.address = address;
      return this;
    }

    public NodeBuilder withLocation(Location location) {
      this.location = location;
      return this;
    }

    public Node build() {
      Node node = new Node();
      node.setNodeId(this.nodeId);
      node.setAddress(this.address);
      node.setLocation(this.location);
      return node;
    }
  }

  public String getNodeId() {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
